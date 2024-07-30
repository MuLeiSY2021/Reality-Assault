package indi.muleisy.ra.pub.utils.redis;

import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({"RedisData", "Key", "Attr"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RedisAnnotationProcessor extends AbstractProcessor {

    private JavacTrees trees;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.trees = JavacTrees.instance(processingEnv);
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(RedisData.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) trees.getTree(element);
                RedisData redisData = element.getAnnotation(RedisData.class);
                String topic = getPrefix(redisData.value().getValue());

                for (Element enclosed : element.getEnclosedElements()) {
                    if (enclosed.getKind() == ElementKind.FIELD) {
                        if (enclosed.getAnnotation(Key.class) != null) {
                            injectKeyMethod(classDecl, enclosed, topic);
                        }
                        if (enclosed.getAnnotation(Attr.class) != null) {
                            injectAttrMethods(classDecl, enclosed);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void injectKeyMethod(JCTree.JCClassDecl classDecl, Element field, String topic) {
        JCTree.JCMethodDecl setKeyMethod = createSetKeyMethod(field, topic);
        classDecl.defs = classDecl.defs.prepend(setKeyMethod);
    }

    private JCTree.JCMethodDecl createSetKeyMethod(Element field, String topic) {
        Name methodName = names.fromString("setKey");
        JCTree.JCVariableDecl param = treeMaker.VarDef(
                treeMaker.Modifiers(Flags.PARAMETER),
                names.fromString(field.getSimpleName().toString()),
                treeMaker.Type(trees.getTree(field).type),
                null
        );

        JCTree.JCExpression setAskKeyExpr = treeMaker.Assign(
                treeMaker.Select(
                        treeMaker.Ident(names.fromString("this")),
                        names.fromString("askKey")
                ),
                treeMaker.Binary(
                        JCTree.Tag.PLUS,
                        treeMaker.Literal(topic+":"),
                        treeMaker.Literal(getPrefix(field.getAnnotation(Key.class).value())+":")
                )
        );

        JCTree.JCStatement setAskKeyStmt = treeMaker.Exec(setAskKeyExpr);
        JCTree.JCStatement setFieldStmt = treeMaker.Exec(
                treeMaker.Assign(
                        treeMaker.Select(
                                treeMaker.Ident(names.fromString("this")),
                                names.fromString(field.getSimpleName().toString())
                        ),
                        treeMaker.Ident(names.fromString(field.getSimpleName().toString()))
                )
        );

        JCTree.JCBlock body = treeMaker.Block(0, List.of(setAskKeyStmt, setFieldStmt));

        return treeMaker.MethodDef(
                treeMaker.Modifiers(Flags.PUBLIC),
                methodName,
                treeMaker.TypeIdent(TypeTag.VOID),
                List.nil(),
                List.of(param),
                List.nil(),
                body,
                null
        );
    }

    private void injectAttrMethods(JCTree.JCClassDecl classDecl, Element field) {
        JCTree.JCMethodDecl getMethod = createGetMethod(field);
        JCTree.JCMethodDecl setMethod = createSetMethod(field);

        classDecl.defs = classDecl.defs.prepend(getMethod);
        classDecl.defs = classDecl.defs.prepend(setMethod);
    }

    private JCTree.JCMethodDecl createGetMethod(Element field) {
        String fieldName = field.getSimpleName().toString();
        Name methodName = names.fromString("get" + capitalize(fieldName));
        JCTree.JCExpression redisKeyExpr = treeMaker.Binary(
                JCTree.Tag.PLUS,
                treeMaker.Select(
                        treeMaker.Ident(names.fromString("this")),
                        names.fromString("askKey")
                ),
                treeMaker.Apply(
                        List.nil(),
                        treeMaker.Select(
                                treeMaker.Ident(names.fromString("this")),
                                names.fromString("getPrefix")
                        ),
                        List.of(treeMaker.Literal(TypeTag.INT, field.getAnnotation(Attr.class).value()))
                )
        );

        // Create the conditional statement
        JCTree.JCExpression condition = treeMaker.Binary(
                JCTree.Tag.EQ,
                treeMaker.Select(
                        treeMaker.Ident(names.fromString("this")),
                        names.fromString(fieldName)
                ),
                treeMaker.Literal(null)
        );

        JCTree.JCExpression ifTrueExpr = treeMaker.Apply(
                List.nil(),
                treeMaker.Select(
                        treeMaker.Ident(names.fromString("jedis")),
                        names.fromString("get")
                ),
                List.of(redisKeyExpr)
        );

        JCTree.JCStatement ifTrueStmt = treeMaker.Return(ifTrueExpr);

        JCTree.JCStatement ifFalseStmt = treeMaker.Return(
                treeMaker.Select(
                        treeMaker.Ident(names.fromString("this")),
                        names.fromString(fieldName)
                )
        );

        JCTree.JCStatement ifStmt = treeMaker.If(condition, ifTrueStmt, ifFalseStmt);

        JCTree.JCBlock body = treeMaker.Block(0, List.of(ifStmt));

        return treeMaker.MethodDef(
                treeMaker.Modifiers(Flags.PUBLIC),
                methodName,
                treeMaker.Ident(names.fromString("String")),
                List.nil(),
                List.nil(),
                List.nil(),
                body,
                null
        );
    }

    private JCTree.JCMethodDecl createSetMethod(Element field) {
        Name methodName = names.fromString("set" + capitalize(field.getSimpleName().toString()));
        JCTree.JCVariableDecl param = treeMaker.VarDef(
                treeMaker.Modifiers(Flags.PARAMETER),
                names.fromString(field.getSimpleName().toString()),
                treeMaker.Type(trees.getTree(field).type),
                null
        );

        JCTree.JCExpression redisKeyExpr = treeMaker.Binary(
                JCTree.Tag.PLUS,
                treeMaker.Select(
                        treeMaker.Ident(names.fromString("this")),
                        names.fromString("askKey")
                ),
                treeMaker.Literal(TypeTag.INT, getPrefix(field.getAnnotation(Attr.class).value()))
        );

        JCTree.JCExpression methodBody = treeMaker.Apply(
                List.nil(),
                treeMaker.Select(
                        treeMaker.Ident(names.fromString("jedis")),
                        names.fromString("set")
                ),
                List.of(redisKeyExpr, treeMaker.Ident(param.name))
        );

        JCTree.JCBlock body = treeMaker.Block(0, List.of(treeMaker.Exec(methodBody)));

        return treeMaker.MethodDef(
                treeMaker.Modifiers(Flags.PUBLIC),
                methodName,
                treeMaker.TypeIdent(TypeTag.VOID),
                List.nil(),
                List.of(param),
                List.nil(),
                body,
                null
        );
    }

    private static String getPrefix(int value) {
        return "prefix_" + value;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
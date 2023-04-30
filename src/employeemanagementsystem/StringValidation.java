package employeemanagementsystem;
import javax.script.*;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.mozilla.javascript.*;
public class StringValidation {
    public static boolean isNumber(String input) throws Exception{
        Context rhino = ContextFactory.getGlobal().enterContext();
        try {
            Scriptable scope = rhino.initStandardObjects();

            // Load the contents of the JavaScript file into a string
            String script = new String(Files.readAllBytes(Paths.get("src/javafxFormValidation.js")), StandardCharsets.UTF_8);

            // Evaluate the JavaScript code in the global scope
            rhino.evaluateString(scope, script, "javafxFormValidation.js", 1, null);

            // Get a reference to the function
            Function func = (Function) scope.get("isNumber", scope);

            // Create an array to hold the function arguments
            Object[] functionArgs = new Object[]{input};

            // Call the function with the arguments
            Object result = func.call(rhino, scope, scope, functionArgs);
            return (boolean) result;

        } finally {
            // Exit the Rhino context
            Context.exit();
        }
    }
    public static boolean verifyStringLength(String input) throws Exception{
        Context rhino = ContextFactory.getGlobal().enterContext();
        try {
            Scriptable scope = rhino.initStandardObjects();

            // Load the contents of the JavaScript file into a string
            String script = new String(Files.readAllBytes(Paths.get("src/javafxFormValidation.js")), StandardCharsets.UTF_8);

            // Evaluate the JavaScript code in the global scope
            rhino.evaluateString(scope, script, "javafxFormValidation.js", 1, null);

            // Get a reference to the function
            Function func = (Function) scope.get("verifyStringLength", scope);

            // Create an array to hold the function arguments
            Object[] functionArgs = new Object[]{input};

            // Call the function with the arguments
            Object result = func.call(rhino, scope, scope, functionArgs);
            return (boolean) result;

        } finally {
            // Exit the Rhino context
            Context.exit();
        }
    }
    public static boolean verifyPhoneLength(String input) throws Exception{
        Context rhino = ContextFactory.getGlobal().enterContext();
        try {
            Scriptable scope = rhino.initStandardObjects();

            // Load the contents of the JavaScript file into a string
            String script = new String(Files.readAllBytes(Paths.get("src/javafxFormValidation.js")), StandardCharsets.UTF_8);

            // Evaluate the JavaScript code in the global scope
            rhino.evaluateString(scope, script, "javafxFormValidation.js", 1, null);

            // Get a reference to the function
            Function func = (Function) scope.get("verifyPhoneLength", scope);

            // Create an array to hold the function arguments
            Object[] functionArgs = new Object[]{input};

            // Call the function with the arguments
            Object result = func.call(rhino, scope, scope, functionArgs);
            return (boolean) result;

        } finally {
            // Exit the Rhino context
            Context.exit();
        }

    }
    public static boolean verifyStringEmpty(String input) throws Exception{
        Context rhino = ContextFactory.getGlobal().enterContext();
        try {
            Scriptable scope = rhino.initStandardObjects();

            // Load the contents of the JavaScript file into a string
            String script = new String(Files.readAllBytes(Paths.get("src/javafxFormValidation.js")), StandardCharsets.UTF_8);

            // Evaluate the JavaScript code in the global scope
            rhino.evaluateString(scope, script, "javafxFormValidation.js", 1, null);

            // Get a reference to the function
            Function func = (Function) scope.get("verifyStringEmpty", scope);

            // Create an array to hold the function arguments
            Object[] functionArgs = new Object[]{input};

            // Call the function with the arguments
            Object result = func.call(rhino, scope, scope, functionArgs);
            return (boolean) result;

        } finally {
            // Exit the Rhino context
            Context.exit();
        }

    }
    public static boolean isAllLetters(String input) throws Exception{
        Context rhino = ContextFactory.getGlobal().enterContext();
        try {
            Scriptable scope = rhino.initStandardObjects();

            // Load the contents of the JavaScript file into a string
            String script = new String(Files.readAllBytes(Paths.get("src/javafxFormValidation.js")), StandardCharsets.UTF_8);

            // Evaluate the JavaScript code in the global scope
            rhino.evaluateString(scope, script, "javafxFormValidation.js", 1, null);

            // Get a reference to the function
            Function func = (Function) scope.get("isAllLetters", scope);

            // Create an array to hold the function arguments
            Object[] functionArgs = new Object[]{input};

            // Call the function with the arguments
            Object result = func.call(rhino, scope, scope, functionArgs);
            return (boolean) result;

        } finally {
            // Exit the Rhino context
            Context.exit();
        }
    }
}

package validator.checkers;

import validator.annotations.Checker;
import validator.annotations.Constrained;
import validator.annotations.ValidationError;
import validator.annotations.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of Validator. Verifies that the terms and conditions of the annotations.
 */
class ValidatorImpl implements Validator {

    /**
     * Validates an object.
     *
     * @param object test object
     * @return errors
     */
    @Override
    public Set<ValidationError> validate(Object object) {

        var errors = new LinkedHashSet<ValidationError>();

        if (!(object instanceof List) &&
                (object == null || object.getClass().getAnnotation(Constrained.class) == null)) {

            System.err.println("object do not have @Constrained annotation.");

        } else {

            checkObject(object, "", errors);
        }

        return errors;
    }

    /**
     * Checks the fields of the object. Collects errors.
     *
     * @param object test object
     * @param path   path
     * @param errors errors
     */
    private void checkObject(Object object, String path, Set<ValidationError> errors) {

        if (!(object instanceof List) &&
                (object == null || object.getClass().getAnnotation(Constrained.class) == null)) {
            // System.err.println("path = '" + path + "' don't have @Constrained annotation");
            return;
        }


        try {

            var fields = object.getClass().getDeclaredFields();

            for (var field : fields) {

                Annotation[] annotations = Arrays.stream(field.getAnnotatedType().getAnnotations())
                        .filter(x -> x.annotationType().getName().startsWith("validator."))
                        .toArray(Annotation[]::new);

                if (annotations.length == 0) {
                    continue;
                }

                field.setAccessible(true);

                Object fieldObj = field.get(object);

                validateObjectWithAnnotations(fieldObj,
                        path + (path.isEmpty() ? "" : ".") + field.getName(), errors, annotations);

                if (fieldObj instanceof List<?>) {

                    validateList((List<?>) fieldObj, path + (path.isEmpty() ? "" : ".") + field.getName(),
                            errors, (AnnotatedParameterizedType) field.getAnnotatedType());
                }

            }

        } catch (Exception e) {

            System.err.println(e.getMessage());
        }

    }

    /**
     * Verifies that the terms and conditions of the annotations. Use special checker classes.
     *
     * @param object      test object
     * @param path        path
     * @param errors      errors
     * @param annotations annotations
     * @throws Exception Catch exception in checkObject
     */
    private void validateObjectWithAnnotations(Object object, String path, Set<ValidationError> errors,
                                               Annotation[] annotations) throws Exception {
        for (var annotation : annotations) {

            if (!annotation.annotationType().getName().startsWith("validator.")) {
                continue;
            }

            var checkerClass = annotation.annotationType().getAnnotation(Checker.class);


            IChecker checker = (IChecker) Class
                    .forName(checkerClass.getCheckerClass())
                    .getDeclaredConstructor()
                    .newInstance();

            if (!checker.isValid(object, annotation)) {

                var e = checker.createValidatorError(object, annotation, path);

                errors.add(e);

                System.err.println(
                        "Invalid value\n\tMessage: " + e.getMessage() + "\n\tPath: " + e.getPath()
                );

            }
//            else {
//                System.err.println(path + " is ok.");
//            }

        }
    }

    /**
     * Checks elements of list in accordance with annotations in parameterizedType.
     *
     * @param list              list
     * @param path              path
     * @param errors            errors
     * @param parameterizedType here is annotations
     * @throws Exception Catch exception in checkObject
     */
    private void validateList(List<?> list, String path, Set<ValidationError> errors,
                              AnnotatedParameterizedType parameterizedType) throws Exception {
        if (list == null || list.size() == 0) {

            return;
        }

        var annotatedActualTypeArguments = parameterizedType.getAnnotatedActualTypeArguments();

        Annotation[] annotations;

        if (annotatedActualTypeArguments != null && annotatedActualTypeArguments.length > 0) {
            annotations = annotatedActualTypeArguments[0].getAnnotations();
        } else {
            annotations = new Annotation[0];
        }


        AnnotatedParameterizedType pt = null;

        for (int i = 0; i < list.size(); i++) {

            validateObjectWithAnnotations(list.get(i), path + ".[" + i + "]", errors, annotations);


            if (pt == null && list.get(i) instanceof List) {

                pt = (AnnotatedParameterizedType) parameterizedType.getAnnotatedActualTypeArguments()[0];
            }

            if (list.get(i) instanceof List) {

                validateList((List<?>) list.get(i), path + ".[" + i + "]", errors, pt);
            } else {

                checkObject(list.get(i), path + ".[" + i + "]", errors);
            }

        }


    }
}

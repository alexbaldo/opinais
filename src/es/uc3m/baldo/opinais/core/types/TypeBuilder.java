package es.uc3m.baldo.opinais.core.types;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sun.reflect.ConstructorAccessor;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

/**
 * TypeBuilder.
 * <p>Provides an interface to dynamically add new values to the
 * Type enumerated.</p>
 * <p>This class uses the power of reflection to alter
 * the enum fields in runtime, in a somehow magical way.</p>
 * 
 * @author Alejandro Baldominos
 */
@SuppressWarnings("restriction")
public class TypeBuilder {

	/*
	 * Initializes the Reflection factory to modify 
	 * class fields dynamically.
	 */
	private static ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();
	
	/**
     * <p>Adds a new type to the Type enumerated.</p>
     * @param enumName the name of the new enum 
     * instance to be added to the class.
     */
    public static void addType (String enumName) {
    	try {
    		// Retrieves and stores the previously existing types.
	        Field valuesField = Type.class.getDeclaredField("ENUM$VALUES");
	        AccessibleObject.setAccessible(new Field[] { valuesField }, true);

            // Previous types are backed up.
            Type[] previousValues = (Type[]) valuesField.get(Type.class);
            List<Type> values = new ArrayList<>(Arrays.asList(previousValues));

            // Builds the new type.
            Type newType = (Type) makeEnum(Type.class, enumName, values.size());

            // Adds the new type to the enumerated.
            values.add(newType);

            // Replaces the old field storing the type with the new one.
            setFailsafeFieldValue(valuesField, values.toArray((Type[]) Array.newInstance(Type.class, 0)));
        } catch (Exception e) { }
    }
	
    /**
     * <p>Builds a new Enum value by retrieving its constructor and
     * calling it by reflection.</p>
     * @param enumClass the class of the enumerated type.
     * @param value the string representation of the enumerated.
     * @param ordinal the numeric representation of the enumerated,
     * which may be an auto-incremental key.
     * @return a new enumerated value.
     * @throws Exception
     */
    private static <T extends Enum<T>> T makeEnum (Class<T> enumClass, String value, int ordinal) throws Exception {
        // Creates the parameters which will be passed to the constructor
    	// of the new enumerated. The first parameters is the string representation
    	// for the new enumerated value. The second argument is internal and stores
    	// a numeric representation for that value.
    	Object[] parms = new Object[] {value, Integer.valueOf(ordinal)};
    	
    	// The constructor for the Enum is retrieved and a new instance is created.
        return (T) enumClass.cast(getConstructorAccessor(enumClass).newInstance(parms));
    }	
    
    /**
     * <p>Returns the constructor for the enumerated
     * type.</p>
     * @param enumClass the class of the enumerated type.
     * @return the constructor for the enumerated type.
     * @throws NoSuchMethodException
     */
    private static ConstructorAccessor getConstructorAccessor(Class<?> enumClass) throws NoSuchMethodException {
        Class<?>[] parameterTypes = {String.class, int.class};
        return reflectionFactory.newConstructorAccessor(enumClass.getDeclaredConstructor(parameterTypes));
    }
    
    /**
     * <p>Alters a field in a class, in a failsafe way.</p> 
     * @param field the field whose value is going to
     * be modified.
     * @param value the new value for the field.
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static void setFailsafeFieldValue (Field field, Object value) throws NoSuchFieldException,
            								   IllegalAccessException {
        // Makes the field accessible.
        field.setAccessible(true);

        // Changes the modifier in the Field instance to not be final 
        // anymore, thus tricking reflection into letting us modify the 
        // static final field.
        // This process is done by removing the final bit in the integer
        // storing the modifiers.
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        int modifiers = modifiersField.getInt(field);
        modifiers &= ~Modifier.FINAL;
        modifiersField.setInt(field, modifiers);

        // Creates the new field. The null value is there because the
        // enumerated is static, so the value is not changed for any
        // particular instance.
        FieldAccessor fa = reflectionFactory.newFieldAccessor(field, false);
        fa.set(null, value);
    } 
}

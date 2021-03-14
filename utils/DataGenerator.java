package com.tactfactory.javaniveau2.tps.tp8.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator<T> {

  private static Random rand = new Random();

  public static String generateRandomString() {
    int maxChar = 20;
    int minChar = 2;

    StringBuilder result = new StringBuilder();
    int nbEl = (rand.nextInt(maxChar - minChar) % maxChar) + minChar;

    for (int i = 0; i < nbEl; i++) {
      result.append((char) ((rand.nextInt(26) % 26) + 97));
    }

    return result.toString();
  }

  public static int generateRandomInt() {
    return Math.abs(rand.nextInt()%10);
  }

  public static long generateRandomLong() {
    return rand.nextLong();
  }

  public static boolean generateRandomBoolean() {
    return rand.nextInt() % 2 == 1;
  }

  public static double generateRandomDouble() {
    return rand.nextDouble();
  }

  public static char generateRandomChar() {
    return (char) ((rand.nextInt(26) % 26) + 97);
  }

  public void generateData(Object item, Class<?> klazz) throws IllegalArgumentException, IllegalAccessException,
      InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
    for (Field field : klazz.getDeclaredFields()) {
      for (Method method : item.getClass().getMethods()) {
        if ((method.getName().startsWith("set")) && (method.getName().length() == (field.getName().length() + 3))) {
          if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
            assignValue(item, field, method, klazz);
          }
        }else if(field.getType().getName().equals(List.class.getName())) {
          if((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))){
            if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
              manageList(item, field, method);
            }
          }
        }
      }
    }
  }

  public void generateDatas(List<?> items, Class<?> klazz) throws IllegalArgumentException, IllegalAccessException,
      InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
    for (Object item : items) {
      this.generateData(item, klazz);
    }
  }

  public List<T> generateList(Class<?> klazz) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    List<T> result = new ArrayList<T>();

    if (checkIfTEqualKlazz(klazz)) {
      for (int i = 0; i < rand.nextInt(200); i++) {
        result.add((T) klazz.getConstructor().newInstance());
      }
    }

    return result;
  }

  private boolean checkIfTEqualKlazz(Class<?> klazz) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

    boolean result = true;
    T data = null;
    try {
      data = (T) klazz.getConstructor().newInstance();
    } catch (ClassCastException e) {
      e.printStackTrace();
      result = false;
    }

    return result;
  }

  private void manageList(Object item, Field field, Method method)
      throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
    List<Object> list = (List<Object>) method.invoke(item);
    ParameterizedType listType = (ParameterizedType) field.getGenericType();
    Class<?> dataType = (Class<?>) listType.getActualTypeArguments()[0];
    Object data = dataType.getConstructor().newInstance();
    list.add(data);

    this.generateDatas(list, dataType);
  }

  private void assignValue(Object item, Field field, Method method, Class<?> klazz)
      throws IllegalAccessException, InvocationTargetException, InstantiationException, IllegalArgumentException,
      NoSuchMethodException, SecurityException {
    if (field.getType().getName().equals(String.class.getName())) {
      method.invoke(item, generateRandomString());
    } else if (field.getType().getName().equals(long.class.getName())
        || field.getType().getName().equals(Long.class.getName())) {
      method.invoke(item, generateRandomLong());
    } else if (field.getType().getName().equals(int.class.getName())
        || field.getType().getName().equals(Integer.class.getName())) {
      method.invoke(item, generateRandomInt());
    } else if (field.getType().getName().equals(boolean.class.getName())
        || field.getType().getName().equals(Boolean.class.getName())) {
      method.invoke(item, generateRandomBoolean());
    } else if (field.getType().getName().equals(double.class.getName())
        || field.getType().getName().equals(Double.class.getName())) {
      method.invoke(item, generateRandomDouble());
    } else if (field.getType().getName().equals(char.class.getName())
        || field.getType().getName().equals(Character.class.getName())) {
      method.invoke(item, generateRandomChar());
    } else {
      Object data = field.getType().getConstructor().newInstance();
      generateData(data, field.getType());
      method.invoke(item, data);
    }
  }
}

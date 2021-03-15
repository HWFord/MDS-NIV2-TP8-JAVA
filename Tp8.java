package com.tactfactory.javaniveau2.tps.tp8;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.tactfactory.javaniveau2.tps.tp8.entities.Post;
import com.tactfactory.javaniveau2.tps.tp8.managers.RestManager;

/**
*
* @author tactfactory
*
*  - Créer un programme qui permet de communiquer avec l'API à l'adresse https://jsonplaceholder.typicode.com/ pour
*     les actions suivantes
*    - 1 : Récupération de la liste de tout les éléments en GET pour une entité donnée
*    - 2 : Récupération d'un élément en GET par son id pour une entité donnée
*    - 3 : Création d'un élément en POST en fournissant le flux JSON de l'élément dans le body de la requête pour une
*       entité donnée
*    - 4 : Mise à jour d'un élément en PUT par son id en fournissant le flux JSON de l'élément dans le body de la
*       requête pour une entité donnée
*    - 5 : Suppression d'un élément en DELETE par son id pour une entité donnée
*  - Le programme doit fonctionner pour toutes les entités implémentant "IdItem" et présentes dans le package entities
*  - Votre code doit appeler, dans Tp8.java, la classe RestManager<T> avec T qui soit un "IdItem", vous avez un exemple
*     commenté d'utilisation pour la classe "Post"
*  - De plus RestManager doit posséder une fonction statique "checkAllEntities" permettant de valider pour toutes les
*     classes implémentant "IdItem" que pour toutes les requêtes le RestManager est bien fonctionnel
*  - Pensez à utiliser la généricité
*  - Les classes utilitaires ClassUtils et DataGenerator sont à votre disposition
*/
public class Tp8 {

  public static void main(String[] args)
      throws MalformedURLException, JAXBException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    RestManager<Post> rmUser = new RestManager<Post>("https://jsonplaceholder.typicode.com/", Post.class);

    try {
      List<Post> posts = rmUser.getRestData().getItems();
      for (Post post : posts) {
        System.out.println(post);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Post post = rmUser.getRestData().getItem(1);
      System.out.println(post);
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Post toUpdate = rmUser.getRestData().getItem(1);
      toUpdate.setTitle("new Title");
      toUpdate.setBody("new Body");
      Post post = rmUser.getRestData().updateItem(toUpdate);
      System.out.println(post);
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Post newPost = new Post();
      newPost.setTitle("new Title");
      newPost.setBody("new Body");
      newPost.setUserId(1);
      newPost.setId(1);
      Post post = rmUser.getRestData().postItem(newPost);
      System.out.println(post);
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      System.out.println(rmUser.getRestData().deleteItem(1));
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(RestManager.checkAllEntities("https://jsonplaceholder.typicode.com/"));
  }
}

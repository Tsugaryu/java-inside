package fr.umlv.java.inside;
/*
 * Slide sur le changement de version
 * L'option --release vérifie et n'accepte que les classes présentes dans la version susmentionnées
 * Donc ne pas mettre de --release permet de voir la v13 et +
 */
public class Example1 {
 
  public static void main(String[] args) {
    final Object monObjet=new Object();
    var scope = new ContinuationScope("hello1");
    var continuation =new Continuation(scope, ()-> {
      System.out.println(Continuation.getCurrentContinuation(scope));
      var c2=Continuation.getCurrentContinuation(scope);
      
      System.out.println( "hello continuation");
      //synchronized (monObjet) {
        Continuation.yield(scope);// fait sortir de la continuation
        
      //}
   
      /*
       * Lorqu'on fait un yield, il faut pouvoir sauvegarder la pile d'exécution et la restaurer lorsqu'on effectue un second run.
       */
      System.out.println( "ara ara");
    });
    continuation.run();
    continuation.isDone();
   // System.out.println(Continuation.getCurrentContinuation(scope));
   // continuation.run();
  }
  /*
   * Question 4 :
   * AjouSystem.out.println(Continuation.getCurrentContinuation(scope));ter un appel à  Continuation.yield(scope); avant le lancement de la continuation lance
   * une exception.
   * Yield quant à lui va s'occuper de bloquer la continuation scope utilisée par la var continuation
   * ce qui l'empêche donc de lancer le runnable. => FAUX
   * 
   */
  /*
   * Une continuation est une sorte de fonction dans laquelle on peut sortir en plein milieu et revenir par la suite 
   */
  /*
   * Que se passe-t'il si on appel run() deux fois et qu'il n'y a pas d'appel à Continuation.yield() à l'intérieur du code de la continuation ?
   * Cela lance une IAE.
     Est-ce le comportement que l'on attend ? 
      Oui car cela est dû à une erreur de programmation.
   */
  /*
   * On peut faire des continuations dans des continuations
   */
  /*
   * Quel est le comportement de l'appel Continuation.getCurrentContinuation(scope) lorsqu'on l'appel à l'intérieur du code de la continuation ou dans le main ?
   * Comportement dans le main :
   * Cela renvoie null.
   * Comportement dans la continuation :
   * Cela renvoie le scope dans lequel on se trouve.
Comparer le comportement de Thread.currentThread() pour une Thread par rapport à Continuation.getCurrentContinuation pour une Continuation. 
   */
  /*
   * On ne peut pas réutiliser la même continuation dans elle même.
   * Pk ? 
   * Lorsqu'on effectue un yield, la pile d'exécution pointe à un certain endroit. Or, si l'on fait cela, la pile ne pourra pas repointer à ce même endroit
   */
  /*
   * Question 9
   * Que se passe t'il lorsque l'on met un bloc synchronized autour d'un appel à Continuation.yield() ? 
   * La continuation passe dans un état détaillant le fait qu'elle est coincé (Pinned)
   * Le problème lorsqu'on rentre dans un synchronized on obtient un monitor qui dit que mon thread ne peut s executer que la
   * De l'autre coté la continuation peut s'amuser à changer de thread lorsqu'elle change de thread avec un yield donc lorsqu'on sortira du synchronized; le thread qui a reçu le jeton ne sera pas le ême que celui qui l'aurait rendu.
   */
  /*
   * 
   */
}

package fr.uvsq.info.poo.coo;

public enum PointsAndCirclesDemo {
    ENVIRONNEMENT;

    /*
     * Methode principale du programme.
     */
    public void run(String[] args) throws InterruptedException {
        // Exemple : Instanciation de cercle et de points
        // tag::pts-and-circles[]
        Point2D p1 = new Point2D(1.0, 2.0);
        Point2D p2 = new Point2D(1.0);
        Point2D p3 = new Point2D();
        Point2D unAutreP3 = p3;
        assert p3 == unAutreP3; // 2 points identiques

        Cercle2D c1 = new Cercle2D(p1, 3.0);
        Cercle2D c2 = new Cercle2D(new Point2D(2.0, 4.0), 5.0);
        Cercle2D c3 = new Cercle2D();
        // end::pts-and-circles[]

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(unAutreP3);

        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
    }

    public static void main(String[] args) throws InterruptedException {
        ENVIRONNEMENT.run(args);
    }
}

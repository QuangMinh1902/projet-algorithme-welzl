package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;


import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {
  public Line calculDiametre(ArrayList<Point> points) {
    // calculDiametre: ArrayList<Point> --> Line
    // renvoie une pair de points de la liste qui forme une distance maximum.

    return diameter(points);
  }
  public Circle calculCercleMin(ArrayList<Point> points) {
    // calculCercleMin: ArrayList<Point> --> Circle
    //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
    //return Ritter(points);
   // return naif(points);
    return welzl(points);
  }

  public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
    if (points.size()<3) {
      return null;
    }
    ArrayList<Point> enveloppe = new ArrayList<Point>();
    enveloppe.add(points.get(0));
    enveloppe.add(points.get(1));
    enveloppe.add(points.get(2));
    return points;
  }

  private Line diameter(ArrayList<Point> points) {
    if (points.size()<2) return null;
    Point p=points.get(0);
    Point q=points.get(1);
    for (Point s: points) for (Point t: points) if (s.distance(t)>p.distance(q)) {p=s;q=t;}
    return new Line(p,q);
  }

  public Circle naif(ArrayList<Point> inputPoints){
    ArrayList<Point> points = (ArrayList<Point>) inputPoints.clone();
    if (points.size()<1) return null;
    double cX,cY,cRadius,cRadiusSquared;
    for (Point p: points){
      for (Point q: points){
        cX = .5*(p.x+q.x);
        cY = .5*(p.y+q.y);
        cRadiusSquared = 0.25*((p.x-q.x)*(p.x-q.x)+(p.y-q.y)*(p.y-q.y));
        boolean allHit = true;
        for (Point s: points)
          if ((s.x-cX)*(s.x-cX)+(s.y-cY)*(s.y-cY)>cRadiusSquared){
            allHit = false;
            break;
          }
        if (allHit) return new Circle(new Point((int)cX,(int)cY),(int)Math.sqrt(cRadiusSquared));
      }
    }

    double resX=0;
    double resY=0;
    double resRadiusSquared=Double.MAX_VALUE;
    for (int i=0;i<points.size();i++){
      for (int j=i+1;j<points.size();j++){
        for (int k=j+1;k<points.size();k++){
          Point p=points.get(i);
          Point q=points.get(j);
          Point r=points.get(k);
          //si les trois sont colineaires on passe
          if ((q.x-p.x)*(r.y-p.y)-(q.y-p.y)*(r.x-p.x)==0) continue;
          //si p et q sont sur la meme ligne, ou p et r sont sur la meme ligne, on les echange
          if ((p.y==q.y)||(p.y==r.y)) {
            if (p.y==q.y){
              p=points.get(k); //ici on est certain que p n'est sur la meme ligne de ni q ni r
              r=points.get(i); //parce que les trois points sont non-colineaires
            } else {
              p=points.get(j); //ici on est certain que p n'est sur la meme ligne de ni q ni r
              q=points.get(i); //parce que les trois points sont non-colineaires
            }
          }
          //on cherche les coordonnees du cercle circonscrit du triangle pqr
          //soit m=(p+q)/2 et n=(p+r)/2
          double mX=.5*(p.x+q.x);
          double mY=.5*(p.y+q.y);
          double nX=.5*(p.x+r.x);
          double nY=.5*(p.y+r.y);
          double alpha1=(q.x-p.x)/(double)(p.y-q.y);
          double beta1=mY-alpha1*mX;
          double alpha2=(r.x-p.x)/(double)(p.y-r.y);
          double beta2=nY-alpha2*nX;
          //le centre c du cercle est alors le point d'intersection des deux droites ci-dessus
          cX=(beta2-beta1)/(double)(alpha1-alpha2);
          cY=alpha1*cX+beta1;
          cRadiusSquared=(p.x-cX)*(p.x-cX)+(p.y-cY)*(p.y-cY);
          if (cRadiusSquared>=resRadiusSquared) continue;
          boolean allHit = true;
          for (Point s: points)
            if ((s.x-cX)*(s.x-cX)+(s.y-cY)*(s.y-cY)>cRadiusSquared){
              allHit = false;
              break;
            }
          if (allHit) {System.out.println("R ="+Math.sqrt(cRadiusSquared));resX=cX;resY=cY;resRadiusSquared=cRadiusSquared;}
        }
      }
    }
    return new Circle(new Point((int)resX,(int)resY),(int)Math.sqrt(resRadiusSquared));
  }

  private Circle Ritter(ArrayList<Point> points){
    if (points.size()<1) return null;
    ArrayList<Point> rest = (ArrayList<Point>)points.clone();
    Point dummy=rest.get(0);
    Point p=dummy;
    for (Point s: rest) if (dummy.distance(s)>dummy.distance(p)) p=s;
    Point q=p;
    for (Point s: rest) if (p.distance(s)>p.distance(q)) q=s;
    double cX=.5*(p.x+q.x);
    double cY=.5*(p.y+q.y);
    double cRadius=.5*p.distance(q);
    rest.remove(p);
    rest.remove(q);
    while (!rest.isEmpty()){
      Point s=rest.remove(0);
      double distanceFromCToS=Math.sqrt((s.x-cX)*(s.x-cX)+(s.y-cY)*(s.y-cY));
      if (distanceFromCToS<=cRadius) continue;
      double cPrimeRadius=.5*(cRadius+distanceFromCToS);
      double alpha=cPrimeRadius/(double)(distanceFromCToS);
      double beta=(distanceFromCToS-cPrimeRadius)/(double)(distanceFromCToS);
      double cPrimeX=alpha*cX+beta*s.x;
      double cPrimeY=alpha*cY+beta*s.y;
      cRadius=cPrimeRadius;
      cX=cPrimeX;
      cY=cPrimeY;
    }
    return new Circle(new Point((int)cX,(int)cY),(int)cRadius);
  }

/** circle de l'algorithme de  Welzl */
  public Circle welzl(ArrayList<Point> points) {
    return CercleMin(points, new ArrayList<Point>());
  }

  private Circle CercleMin(ArrayList<Point> input, ArrayList<Point> R ) {
    //P : liste des points donnée
    //R : liste des points sur la frontière et vide

    ArrayList<Point> P = new ArrayList<Point>(input);
    Random random = new Random();
    Circle cercle = null;

    if (P.isEmpty() || R.size() == 3) {
      cercle = bmd(new ArrayList<Point>(), R);

    } else {
      Point p = P.get((random.nextInt(P.size())));
      P.remove(p);

      //recursive : la liste P est modifié
      cercle = CercleMin(P, R);

      if (cercle != null && !estInterieur(cercle, p)) {
        R.add(p);
        cercle = CercleMin(P, R);
        R.remove(p);
      }
    }

    return cercle;
  }

  private boolean estInterieur(Circle c, Point p) {
    if (p.distance(c.getCenter()) - c.getRadius() < 0.00001) {
      return true;
    }
    return false;
  }


  // dessiner la base minimum disk, un cercle le minimum de point
  private Circle bmd(ArrayList<Point> P, ArrayList<Point> R) {
    if (P.isEmpty() && R.size() == 0)
      return new Circle(new Point(0, 0), 10);
    Random r = new Random();
    Circle cercle = null;
    // Si il y a un seul point dans la liste , le cercle est .... ce point
    if (R.size() == 1) {
      cercle = new Circle(R.get(0), 0);
    }

    //Si il y a 2 point, le cercle sera formé avec le centre étant le milieu de la distance de ces 2 points
    if (R.size() == 2) {
      double cx = (R.get(0).x + R.get(1).x) / 2;
      double cy = (R.get(0).y + R.get(1).y) / 2;
      double d = R.get(0).distance(R.get(1)) / 2;
      Point p = new Point((int) cx, (int) cy);
      cercle = new Circle(p, (int) Math.ceil(d));
    } else {
      if (R.size() == 3)
        cercle = cercle_avec_3point(R.get(0), R.get(1), R.get(2));
    }

    return cercle;
  }

  //maths : pour calculer la valeur au carré de chaque coordonnée
  private int norm(Point a) {
    return (a.x * a.x) + (a.y * a.y);
  }

  //dessiner un cercle avec 3 point
  private Circle cercle_avec_3point(Point a, Point b, Point c) {
    // Calcul du déterminant pour vérifier si les points sont colinéaires.
    double d = (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) * 2;

    // Si les points sont colinéaires, aucun cercle unique ne peut être formé.
    if (d == 0) return null;

    double x = ((norm(a) * (b.y - c.y)) + (norm(b) * (c.y - a.y)) + (norm(c) * (a.y - b.y))) / d;

    double y = ((norm(a) * (c.x - b.x)) + (norm(b) * (a.x - c.x)) + (norm(c) * (b.x - a.x))) / d;

    Point p = new Point((int) x, (int) y);

    //constructeur du Circle a besoind un type de int
    // Math.ceil pour s'assurer que le rayon du cercle est assez grand pour inclure le point a, même si la distance exacte est un nombre non entier.
    return new Circle(p, (int) Math.ceil(p.distance(a)));
  }

}

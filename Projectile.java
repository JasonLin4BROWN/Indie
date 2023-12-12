package indie;
/**
 * This is the Projectile interface, it enables polymorphism of our projectiles and standardizes their methods
 */
public interface Projectile {
    //for the time being I will not implement anything
    public void spawn();
    public void hunt();
    public void kill();
    public void moveLeft();
    public void moveRight();
    public void rise();
    public void fall();
    public void despawn();
    public void deathClock();




    }

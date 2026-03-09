package utilz;

public class Maths {
    public static float Lerp(float a, float b, float t) {
        return a + t * (b - a);
    }
    public static int Distance(float x1, float y1, float x2, float y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return (int)Math.sqrt(dx * dx + dy * dy);
    }

}

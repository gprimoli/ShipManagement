package model.Util;

public class BuilderPatternSpiegazione {
    private int x;
    private int y;

    private BuilderPatternSpiegazione(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static class CostruttoreTest{
        private int x;
        private int y;

        public CostruttoreTest x(int x){
            this.x = x;
            return this;
        }
        public CostruttoreTest y(int y){
            this.y = y;
            return this;
        }
        public BuilderPatternSpiegazione build(){
            return new BuilderPatternSpiegazione(x,y);
        }
    }

   /* public static void main(String[] args) {
        BuilderPatternSpiegazione t = new BuilderPatternSpiegazione.CostruttoreTest().y(12).x(15).build();
        t.getX();
    }*/

}

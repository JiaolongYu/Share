package apt;
import junit.framework.TestCase;

public class RationalTest extends TestCase {

    protected Rational HALF;

    protected void setUp() {
      HALF = new Rational( 1, 2 );
    }

    // Create new test
    public RationalTest (String name) {
        super(name);
    }
    
    public void testEquality() {
        assertEquals(new Rational(1,3), new Rational(1,3));
        assertEquals(new Rational(1,3), new Rational(2,6));
        assertEquals(new Rational(3,3), new Rational(1,1));
        assertEquals(new Rational(0,3), new Rational(0,1));
        assertFalse(new Rational(0,3).equals(null));
        assertFalse(new Rational(0,3).equals(0));
    }

    // Test for nonequality
    public void testNonEquality() {
        assertFalse(new Rational(2,3).equals(
            new Rational(1,3)));
        assertFalse(new Rational(-2,3).equals(
                new Rational(2,3)));
    }
    
    public void testTimes() {
    	assertTrue(new Rational(1,-1).times(new Rational(1,1)).equals(new Rational(-1,1)));
    	assertTrue(new Rational(6,4).times(new Rational(2,3)).equals(new Rational(1,1)));
    	assertTrue(new Rational(6,4).times(new Rational(0,3)).equals(new Rational(0,4)));
    	try{
    		assertTrue(new Rational(0,1).times(new Rational(0,1)).equals(new Rational(0,1)));
    	}catch (Exception e){
    		assertTrue(false);
    	}
    }
    
    public void testPlus() {
    	try{
    		assertTrue(new Rational(0,1).plus(new Rational(0,1)).equals(new Rational(0,1)));
    	}catch (Exception e){
    		assertTrue(false);
    	}
    }

    public void testMinus(){
    	try{
    		assertTrue(new Rational(0,1).minus(new Rational(0,1)).equals(new Rational(0,1)));
    	}catch (Exception e){
    		assertTrue(false);
    	}
    }

    public void testDivides(){
        assertTrue(new Rational(0,1).divides(new Rational(1,1)).equals(new Rational(0,1)));
        assertTrue(new Rational(4,1).divides(new Rational(6,6)).equals(new Rational(4,1)));
    }
    
    

    public void testAccessors() {
    	assertEquals(new Rational(2,3).numerator(), 2);
    	assertEquals(new Rational(2,3).denominator(), 3);
    	assertEquals(new Rational(4,6).denominator(), 3);
    	assertEquals(new Rational(0,6).numerator(), 0);
    	assertEquals(new Rational(0,6).denominator(), 1);
    	assertEquals(new Rational(-2,6).numerator(), -1);
    	assertEquals(new Rational(2,-6).denominator(), -3);
    	assertEquals(new Rational(2,-6).numerator(), 1);
    }
    
    public void testTolerance(){
    	Rational.setTolerance(new Rational(2,3));
    	assertTrue(Rational.getTolerance().equals(new Rational(4,6)));
    }
    
    public void testAbs(){
    	Rational s = new Rational(1,2);
    	Rational s1 = new Rational(-1,2);
    	Rational s2 = new Rational(-1,-2);
    	Rational s3 = new Rational(0,-2);
    	assertTrue(s.abs().equals(new Rational(2,4)));
    	assertTrue(s1.abs().equals(new Rational(2,4)));
    	assertTrue(s2.abs().equals(new Rational(2,4)));
    	assertTrue(s3.abs().equals(new Rational(0,4)));
    }

    public void testRoot() {
        Rational s = new Rational( 1, 4 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }
        assertTrue( sRoot.isLessThan( HALF.plus( Rational.getTolerance() ) ) 
                        && HALF.minus( Rational.getTolerance() ).isLessThan( sRoot ) );
    }
    
    public void testRoot1() {
        Rational s = new Rational( 0, 4 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }
        Rational t = new Rational(0,1);
        assertTrue( sRoot.isLessThan( t.plus( Rational.getTolerance() ) ) 
                        && t.minus( Rational.getTolerance() ).isLessThan( sRoot ) );
    }
    
    public void testRoot2() {
        Rational s = new Rational( 4, 1 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }
        Rational t = new Rational(2,1);
        assertTrue( sRoot.isLessThan( t.plus( Rational.getTolerance() ) ) 
                        && t.minus( Rational.getTolerance() ).isLessThan( sRoot ) );
    }
    
    public void testRoot3() {
        Rational s = new Rational( -4, 1 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
            assert false;
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
            assert true;
        }
    }
    
    public void testIslessthan(){
    	Rational s = new Rational(2,3);
    	assertTrue(s.isLessThan(new Rational(5,6)));
    	
    }
    
    public void testConstruct(){
    	Rational s = new Rational(new Rational(-2,4));
    	assertEquals(s, new Rational(1,-2));
    }
    
    public void testNotlessthan(){
    	Rational s = new Rational(2,3);
    	assertFalse(s.isLessThan(new Rational(1,6)));
    }
    
    public void testTostring(){
    	Rational s = new Rational(2,3);
    	assertEquals(s.toString(),"2/3");
    }
    
    public void testPlusOverflow(){
    	Rational sum = new Rational(Integer.MAX_VALUE,1).plus(new Rational(Integer.MAX_VALUE,1));
    	assertFalse(new Rational(-2,1).equals(sum));
    }
    
    public void testTimesOverflow(){
    	Rational prod = new Rational(Integer.MAX_VALUE,1).times(new Rational(2,1));
    	assertFalse(new Rational(-2,1).equals(prod));
    }
    
    public void testMinusOverflow(){
    	Rational sub = new Rational(3*Integer.MAX_VALUE,1).minus(new Rational(Integer.MAX_VALUE,1));
    	assertFalse(new Rational(-2,1).equals(sub));
    }
    
    
    public void testDivideOverflow(){
        assertEquals(new Rational(Integer.MAX_VALUE,1),new Rational(Integer.MAX_VALUE*2,1).divides(new Rational(2,1)));
    }
    
    public void testMain(){
    	Rational.main(null);
    }

    public static void main(String args[]) {
        String[] testCaseName = { RationalTest.class.getName() };
        // junit.swingui.TestRunner.main(testCaseName);
        junit.textui.TestRunner.main(testCaseName);
    }
}

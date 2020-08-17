package Chapter301ModelAndPrototype.Section401DesignPattern.StructuralPattern.Facade;

public class Facade {
    private SubSystem subSystem = new SubSystem();
    
    public void watchMovie() {
        subSystem.turnOnTV();
        subSystem.setCD("a movie");
        subSystem.startWatching();
    }
}

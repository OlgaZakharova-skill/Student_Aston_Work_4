public class LiveLock {
    private String name;
    private boolean transition;
    private boolean passesNext;

    public LiveLock(String name, boolean transition) {
        this.name = name;
        this.transition = transition;
        this.passesNext = false;
    }

    public void tryToGo(LiveLock other) {
        while (!passSuccess(other)) {
            System.out.println(name + " passage " + (transition ? "right" : "left"));
            if(other.passesNext){
                transition = !transition;
                System.out.println(name + " redirect " + (transition ? "right" : "left"));
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            passesNext = !passesNext;
        }
        System.out.println(name + " successfully");
    }

    public boolean passSuccess(LiveLock other) {
        return (this.transition != other.transition) ||
                (this.passesNext != other.passesNext);
    }
}

package GeneralCode;

public interface OnGetWorkListener {
    int RESTART = 1;
    int SHOW = 2;
    int HIDE = 3;

    void workSomething(int work_id);
}


public class MainApplication {

    public static void main(String[] args) {

        GetSKUInfo task = new GetSKUInfo();

//        task.runTask();

        GenerateData generateData = new GenerateData();
        generateData.generate();

    }

}

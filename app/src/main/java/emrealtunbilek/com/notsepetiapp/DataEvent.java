package emrealtunbilek.com.notsepetiapp;

/**
 * Created by Emre Altunbilek on 16.10.2017.
 */

public class DataEvent {

    public static class NotEkleDialogGoster{

        private int tetikle;

        public int getTetikle() {
            return tetikle;
        }

        public void setTetikle(int tetikle) {
            this.tetikle = tetikle;
        }

        public NotEkleDialogGoster(int tetikle) {
            this.tetikle = tetikle;
        }
    }


    public static class DataGuncelleMethoduTetikle{

        private int tetikle;

        public int getTetikle() {
            return tetikle;
        }

        public void setTetikle(int tetikle) {
            this.tetikle = tetikle;
        }

        public DataGuncelleMethoduTetikle(int tetikle) {
            this.tetikle = tetikle;
        }
    }
}

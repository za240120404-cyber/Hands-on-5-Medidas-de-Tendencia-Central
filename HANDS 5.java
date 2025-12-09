public class Main {

    public static void main(String[] args) {
        HandsOn5.run();
    }

    // ==========================
    //        HANDS-ON 5
    //  Medidas de Tendencia
    // ==========================
    static class HandsOn5 {

        public static void run() {

            double[] dataset = {
                25,12,44,30,28,55,37,60,62,70,
                58,21,29,35,41,50,67,45,32,34,
                39,57,48,52,33,31,22,26,36,40,49
            };

            int n = dataset.length;
            int k = 6;

            double min = dataset[0], max = dataset[0];
            for (double v : dataset) {
                if (v < min) min = v;
                if (v > max) max = v;
            }

            double range = max - min;
            double width = Math.ceil(range / k);

            int[] freq = new int[k];
            double[] lower = new double[k];
            double[] upper = new double[k];
            double[] xc = new double[k];

            for (int i = 0; i < k; i++) {
                lower[i] = min + i * width;
                upper[i] = lower[i] + width - 1;
            }

            for (double value : dataset) {
                for (int i = 0; i < k; i++) {
                    if (value >= lower[i] && value <= upper[i]) {
                        freq[i]++;
                        break;
                    }
                }
            }

            int Fa = 0;
            System.out.println("Clase\tLinf\tLsup\tFreq\tXc\tFa");
            for (int i = 0; i < k; i++) {
                xc[i] = (lower[i] + upper[i]) / 2;
                Fa += freq[i];

                System.out.println((i+1) + "\t" + lower[i] + "\t" +
                        upper[i] + "\t" + freq[i] + "\t" +
                        xc[i] + "\t" + Fa);
            }

            System.out.println("\n===== MEDIDAS DE TENDENCIA CENTRAL =====");

            double media = calcMedia(freq, xc, n);
            System.out.println("Media = " + String.format("%.2f", media));

            double mediana = calcMediana(freq, lower, width, n);
            System.out.println("Mediana = " + String.format("%.2f", mediana));

            double moda = calcModa(freq, lower, width);
            System.out.println("Moda = " + String.format("%.2f", moda));
        }

        // =====================================
        //              MEDIA
        // =====================================
        static double calcMedia(int[] f, double[] xc, int n) {
            double sum = 0;
            for (int i = 0; i < f.length; i++) {
                sum += f[i] * xc[i];
            }
            return sum / n;
        }

        // =====================================
        //              MEDIANA
        // =====================================
        static double calcMediana(int[] f, double[] lower, double width, int n) {

            double N2 = n / 2.0;
            int Fa = 0;
            int classIndex = 0;

            for (int i = 0; i < f.length; i++) {
                Fa += f[i];
                if (Fa >= N2) {
                    classIndex = i;
                    break;
                }
            }

            int Fa_prev = 0;
            for (int i = 0; i < classIndex; i++) Fa_prev += f[i];

            return lower[classIndex] +
                    ((N2 - Fa_prev) / f[classIndex]) * width;
        }

        // =====================================
        //               MODA
        // =====================================
        static double calcModa(int[] f, double[] lower, double width) {

            int modalClass = 0;
            for (int i = 1; i < f.length; i++) {
                if (f[i] > f[modalClass]) modalClass = i;
            }

            int f0 = f[modalClass];
            int fPrev = modalClass > 0 ? f[modalClass - 1] : 0;
            int fNext = modalClass < f.length - 1 ? f[modalClass + 1] : 0;

            return lower[modalClass] +
                    ((double)(f0 - fPrev) / ((2 * f0) - fPrev - fNext)) * width;
        }
    }
}

package appiumrunner.unesc.net.appiumrunner.models;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static List<Motorista> motoristaList = new ArrayList<>();

    public static void addOrUptate(Motorista motorista) {
        if (motorista != null) {
            int index = -1;

            motoristaList.indexOf(motorista);
            for (Motorista m : motoristaList) {
                if (m.getCodigo() == motorista.getCodigo()) {
                    index = motoristaList.indexOf(m);
                    break;
                }
            }

            if (index > -1) {
                motoristaList.set(index, motorista);
            } else {
                motoristaList.add(motorista);
            }
        }
    }

    public static void remove(Motorista motorista) {
        if (motorista != null) {
            motoristaList.remove(motorista);
        }
    }

    public static List<Motorista> getMotoristaList() {
        return motoristaList;
    }
}

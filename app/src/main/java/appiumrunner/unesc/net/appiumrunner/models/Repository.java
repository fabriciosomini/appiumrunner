package appiumrunner.unesc.net.appiumrunner.models;

import java.util.ArrayList;
import java.util.List;

import appiumrunner.unesc.net.appiumrunner.adapters.MotoristaAdapter;

public class Repository {

    private static List<Motorista> motoristaList = new ArrayList<>();
    private static MotoristaAdapter adapter;

    public static void setAdapter(MotoristaAdapter adapter) {
        Repository.adapter = adapter;
    }

    public static void addOrUptate(Motorista motorista) {
        if (motorista != null) {
            int index = getIndexOf(motorista);

            if (index > -1) {
                motoristaList.set(index, motorista);
            } else {
                motoristaList.add(motorista);
            }
        }
        adapter.clear();
        adapter.addAll(motoristaList);
        adapter.notifyDataSetChanged();
    }

    private static int getIndexOf(Motorista motorista) {
        int index = -1;
        for (Motorista m : motoristaList) {
            if (m.getCodigo() == motorista.getCodigo()) {
                index = motoristaList.indexOf(m);
                break;
            }
        }
        return index;
    }

    public static void remove(Motorista motorista) {
        if (motorista != null) {
            int index = getIndexOf(motorista);
            if (index > -1) {
                motoristaList.remove(index);
            }
        }

        adapter.clear();
        adapter.addAll(motoristaList);
        adapter.notifyDataSetChanged();
    }

    public static List<Motorista> getMotoristaList() {
        return motoristaList;
    }
}

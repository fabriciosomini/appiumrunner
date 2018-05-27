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

    public static int addOrUptate(Motorista motorista) {
        int added = -1;
        if (motorista != null) {
            int index = getIndexOf(motorista);

            if (index > -1) {
                motoristaList.set(index, motorista);
                added = 1;
            } else {
                motoristaList.add(motorista);
                added = 0;
            }
        }
        adapter.clear();
        adapter.addAll(motoristaList);
        adapter.notifyDataSetChanged();

        return added;
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

    public static int remove(Motorista motorista) {
        int removed = -1;
        if (motorista != null) {
            int index = getIndexOf(motorista);
            if (index > -1) {
                motoristaList.remove(index);
                removed = 0;
            }
        }

        adapter.clear();
        adapter.addAll(motoristaList);
        adapter.notifyDataSetChanged();
        return removed;
    }

    public static List<Motorista> getMotoristaList() {
        return motoristaList;
    }

    public static boolean contains(Motorista motorista) {
        for (Motorista m :
                motoristaList) {
            if (m.getCodigo() == motorista.getCodigo()) {
                return true;
            }
        }
        return false;
    }
}

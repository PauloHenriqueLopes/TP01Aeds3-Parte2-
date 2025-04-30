package modelo;

import java.io.File;
import java.util.ArrayList;

import aeds3.*;
import entidades.Atores;

public class ArquivoAtor extends Arquivo<Atores> {

    ArvoreBMais<ParNomeId> indiceNome;
    ArvoreBMais<ParIdId> indiceAtorSerie;

    public ArquivoAtor() throws Exception {
        super("atores", Atores.class.getConstructor());
        File diretorio = new File("./dados/atores");
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
        indiceNome = new ArvoreBMais<>(ParNomeId.class.getConstructor(), 5, "./dados/atores/indiceNome.db");
        indiceAtorSerie = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, "./dados/atores/indiceAtorSerie.db");
    }

    @Override
    public int create(Atores a) throws Exception {
        int id = super.create(a);
        indiceNome.create(new ParNomeId(a.getName(), id));
        String[] idsSerie = a.getIdSerie().split(",");
        for (String idSerieStr : idsSerie) {
            if (!idSerieStr.trim().isEmpty()) {
                int idSerie = Integer.parseInt(idSerieStr.trim());
                indiceAtorSerie.create(new ParIdId(idSerie, id));
            }
        }
        return id;
    }

    public Atores[] readNome(String nome) throws Exception {
        if (nome.length() == 0)
            return null;
        
        ArrayList<ParNomeId> pnis = indiceNome.read(new ParNomeId(nome, -1));
        if (pnis.size() > 0) {
            Atores[] atores = new Atores[pnis.size()];
            int i = 0;
            for (ParNomeId pni : pnis) {
                atores[i++] = read(pni.getId());
            }
            return atores;
        } else
            return null;
    }

    public Atores[] readAll() throws Exception {
        ArrayList<Atores> atores = new ArrayList<>();

        int ultimoID = super.ultimoID();

        for (int id = 1; id <= ultimoID; id++) {
            Atores a = super.read(id);
            if (a != null) {
                atores.add(a);
            }
        }

        return atores.toArray(new Atores[0]);
    }

    @Override
    public boolean delete(int id) throws Exception {
        Atores a = read(id);
        if (a != null) {
            if (super.delete(id)) {
                return indiceNome.delete(new ParNomeId(a.getName(), id));
            }
        }
        return false;
    }

    public boolean delete(String nome) throws Exception {
        ArrayList<ParNomeId> pnis = indiceNome.read(new ParNomeId(nome, -1));
        if (pnis != null && pnis.size() > 0) {
            return delete(pnis.get(0).getId());
        }
        return false;
    }

    @Override
    public boolean update(Atores novoAtor) throws Exception {
        Atores a = read(novoAtor.getId());
        if (a != null) {
            if (super.update(novoAtor)) {
                if (!a.getName().equals(novoAtor.getName())) {
                    indiceNome.delete(new ParNomeId(a.getName(), a.getId()));
                    indiceNome.create(new ParNomeId(novoAtor.getName(), novoAtor.getId()));
                }
                return true;
            }
        }
        return false;
    }
}
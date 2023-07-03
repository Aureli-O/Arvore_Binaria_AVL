import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ArvoreBinariaAVLHash {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        ArvoreBinariaAVL arvore = new ArvoreBinariaAVL();

        //Troque aqui o arquivo que deseja ser colocado na árvore
        //No próprio repositório tem diversos arquivos para testar
        File file = new File("ARQUIVO AQUI//FILE HERE");
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    arvore.adiciona(sha1(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        arvore.exibirCalcularHashFinal();
        //arvore.exibirEmOrdemDecrescente();
    }
    static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}

class ArvoreBinariaAVL {
    public NoABAVL raiz;

    public ArvoreBinariaAVL() {
        raiz = null;
    }

    public void exibirCalcularHashFinal() throws NoSuchAlgorithmException {
        String hashFinal = calcularHashFinal(raiz);
        System.out.println("Hash da Raiz: " + hashFinal);
    }
    private String calcularHashFinal(NoABAVL no) throws NoSuchAlgorithmException {
        if(raiz.esquerdo != null && raiz.direito != null){
            System.out.println(no.dado + (raiz.esquerdo.dado) + (raiz.direito.dado));
            return ArvoreBinariaAVLHash.sha1(no.dado + (raiz.esquerdo.dado) + (raiz.direito.dado));
        }else{
            if(raiz.esquerdo != null){
                return ArvoreBinariaAVLHash.sha1(no.dado + calcularHashFinal(raiz.esquerdo));
            } else if (raiz.direito != null) {
                return ArvoreBinariaAVLHash.sha1(no.dado + calcularHashFinal(raiz.direito));
            }else {
                return raiz.dado;
            }
        }
    }
    private String calcularHash(NoABAVL no) throws NoSuchAlgorithmException {
        if(no.esquerdo!= null && no.direito != null){
            if(no.altura >= 1){
                return ArvoreBinariaAVLHash.sha1(no.dado + (raiz.esquerdo.dado) + (raiz.direito.dado));
            }else{
                return raiz.dado;
            }
        }
        return null;
    }
    public void adiciona(String elemento) {
        if (raiz == null) {
            raiz = new NoABAVL(elemento);
        } else {
            raiz = adiciona(elemento, raiz);
        }
    }

    private NoABAVL adiciona(String elemento, NoABAVL no) {
        if (no == null) {
            return new NoABAVL(elemento);
        }

        if (elemento.compareTo(no.dado) < 0) {
            no.esquerdo = adiciona(elemento, no.esquerdo);
        } else if (elemento.compareTo(no.dado) > 0) {
            no.direito = adiciona(elemento, no.direito);

        } else {
            return no;
        }

        no.altura = 1 + Math.max(getAltura(no.esquerdo), getAltura(no.direito));

        int FatorDeBalanceamento = getFatorDeBalanceamento(no);

        if (FatorDeBalanceamento > 1) {
            if (elemento.compareTo(no.esquerdo.dado) < 0) {
                return rotacaoDireita(no);
            } else {
                no.esquerdo = rotacaoEsquerda(no.esquerdo);
                return rotacaoDireita(no);
            }
        } else if (FatorDeBalanceamento < -1) {
            if (elemento.compareTo(no.direito.dado) > 0) {
                return rotacaoEsquerda(no);
            } else {
                no.direito = rotacaoDireita(no.direito);
                return rotacaoEsquerda(no);
            }
        }

        return no;
    }

    private int getAltura(NoABAVL no) {
        if (no == null) {
            return 0;
        }
        return no.altura;
    }

    private int getFatorDeBalanceamento(NoABAVL no) {
        if (no == null) {
            return 0;
        }
        return getAltura(no.esquerdo) - getAltura(no.direito);
    }

    private NoABAVL rotacaoEsquerda(NoABAVL x) {
        NoABAVL y = x.direito;
        NoABAVL NoTemp = y.esquerdo;

        y.esquerdo = x;
        x.direito = NoTemp;

        x.altura = 1 + Math.max(getAltura(x.esquerdo), getAltura(x.direito));
        y.altura = 1 + Math.max(getAltura(y.esquerdo), getAltura(y.direito));

        return y;
    }

    private NoABAVL rotacaoDireita(NoABAVL y) {
        NoABAVL x = y.esquerdo;
        NoABAVL NoTemp = x.direito;

        x.direito = y;
        y.esquerdo = NoTemp;

        y.altura = 1 + Math.max(getAltura(y.esquerdo), getAltura(y.direito));
        x.altura = 1 + Math.max(getAltura(x.esquerdo), getAltura(x.direito));

        return x;
    }

    private boolean verificarBalanceamento(NoABAVL no) {
        if (no == null) {
            return true;
        }

        int FatorDeBalanceamento = getFatorDeBalanceamento(no);

        if (FatorDeBalanceamento > 1 || FatorDeBalanceamento < -1) {
            return false;
        }

        return verificarBalanceamento(no.esquerdo) && verificarBalanceamento(no.direito);
    }

    public void exibir() {
        exibir(raiz);
        System.out.println();
    }

    private void exibir(NoABAVL raiz) {


        if (raiz.esquerdo != null) {
            exibir(raiz.esquerdo);
        }


        if (raiz.direito != null) {
            exibir(raiz.direito);
        }
        System.out.println(raiz.dado + " " + "(" +raiz.altura + ")");

    }

    public void exibirEmOrdemDecrescente() {
        exibirEmOrdemDecrescente(raiz);
        System.out.println();
    }
    private void exibirEmOrdemDecrescente(NoABAVL raiz) {
        if (raiz == null) {
            return;
        }

        exibirEmOrdemDecrescente(raiz.direito);
        System.out.println(raiz.dado + " (" + raiz.altura + ")");
        exibirEmOrdemDecrescente(raiz.esquerdo);
    }
}

class NoABAVL {
    public String dado;
    public int altura;
    public NoABAVL esquerdo;
    public NoABAVL direito;
    public NoABAVL pai;

    public NoABAVL(String dado) {
        this.dado = dado;
        this.esquerdo = null;
        this.direito = null;
        this.altura = 1;
    }
}
package com.example.rodrigobresan.hackaton.models;

/**
 * Created by Rodrigo Bresan on 26/09/2015.
 */
public class Votes {
    private String codCidade;
    private int positiveVotes;
    private int negativeVotes;

    public String getCodCidade() {
        return codCidade;
    }

    public void setCodCidade(String codCidade) {
        this.codCidade = codCidade;
    }

    public int getPositiveVotes() {
        return positiveVotes;
    }

    public void setPositiveVotes(int positiveVotes) {
        this.positiveVotes = positiveVotes;
    }

    public int getNegativeVotes() {
        return negativeVotes;
    }

    public void setNegativeVotes(int negativeVotes) {
        this.negativeVotes = negativeVotes;
    }
}

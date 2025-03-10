package agh.ics.oop.model.elements;

import agh.ics.oop.model.enums.GenomeVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.round;

public class Genotype {
    List<Integer> genome;
    private int currentGenomeIndex;
    private final int genomeSize;

    public Genotype(int genomeSize){
        this.genome = new ArrayList<>();
        this.genomeSize = genomeSize;
        for (int i = 0; i < genomeSize; i++){
            this.genome.add((int) round(Math.random()*7));
        }
        this.currentGenomeIndex = (int) round(Math.random()*(genomeSize-1));
    }

    public void setCurrentGenomeIndex(int currentGenomeIndex) {
        this.currentGenomeIndex = currentGenomeIndex;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public void changeGenome(int index, int value){
        List<Integer> tempGenome = new ArrayList<>(this.genome.subList(0, index));
        tempGenome.add(value);
        tempGenome.addAll(this.genome.subList(index + 1, this.genome.size()));
        this.genome = tempGenome;
    }

    public int getCurrentGenomeIndex() {
        return currentGenomeIndex;
    }

    public void indexChange(GenomeVariant genomeVariant) {
        switch (genomeVariant){
            case NORMAL -> {
                setCurrentGenomeIndex((this.currentGenomeIndex+1) % genomeSize);}
            case CRAZY -> {
                if(Math.random() <= 0.2){
                    setCurrentGenomeIndex((int) round(Math.random()* (genomeSize-1)));
                }
                else{
                    setCurrentGenomeIndex((this.currentGenomeIndex+1) % genomeSize);
                }
            }
        }
    }

    public int getGenomeSize() {
        return genomeSize;
    }

    public Genotype createChildGenotype(Genotype otherParentGenotype, float energyPercent){
        int partIndex = (int)(energyPercent * genomeSize);

        int genomeSize = otherParentGenotype.getGenomeSize();
        Genotype childGenotype = new Genotype(genomeSize);
        double genomeSide = Math.random();
        List<Integer> childGenome = new ArrayList<>();
        if(genomeSide < 0.5){
            childGenome.addAll(this.genome.subList(0, partIndex));
            childGenome.addAll(otherParentGenotype.genome.subList(partIndex, genomeSize));
        }
        else{
            childGenome.addAll(otherParentGenotype.genome.subList(0, partIndex));
            childGenome.addAll(this.genome.subList(partIndex, genomeSize));
        }
        childGenotype.genome = childGenome;

        return childGenotype;
    }

    public String toString() {
        return genome.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return Objects.equals(genome, genotype.genome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genome);
    }
}

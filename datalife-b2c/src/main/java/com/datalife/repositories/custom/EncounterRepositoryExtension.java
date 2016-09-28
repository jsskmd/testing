package com.datalife.repositories.custom;

import com.datalife.entities.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by supriya gondi on 11/7/2014.
 *
 * This Custom repository handles Encounter entity related Custom logics
 */
public interface EncounterRepositoryExtension {

    /**
     * This method is for set EncounterId for ReviewofSystem Object before assigning to Encounter
     *
     * @param reviewofSystems
     * @param encounter
     * @return List of ReviewofSystem
     */
    public List<ReviewofSystems> getRos(List<ReviewofSystems> reviewofSystems, Encounter encounter);

    /**
     * This method is for set EncounterId for PhysicalExamination Object before assigning to Encounter
     *
     * @param physicalExaminations
     * @param encounter
     * @return List of PhysicalExamination
     */
    public List<PhysicalExamination> getPe(List<PhysicalExamination> physicalExaminations, Encounter encounter);

    /**
     * This method is for set EncounterId for LabOrder Object before assigning to Encounter
     *
     * @param labOrders
     * @param encounter
     * @return List of LabOrder
     */
    public List<LabOrder> getLabOrders(List<LabOrder> labOrders, Encounter encounter);

    /**
     * This method is for set EncounterId for Prescription Object before assigning to Encounter
     *
     * @param prescriptions
     * @param encounter
     * @return List of Prescription
     */
    public List<Prescription> getPrescriptions(List<Prescription> prescriptions, Encounter encounter);

    /*public boolean saveEncounter1(Encounter encounter);*/
    public boolean saveConsultation(Encounter encounter);
    public History setHistory(History history, User patient);
    public Encounter saveEncounter(Encounter encounter);
    public void generatePdfAndUploadToPatient(Encounter encounter,User doctor,User patient);

}

# Pharmacist Prescribing Tool

## What will the application do?
The application will allow pharmacists to efficiently prescribe medications 
for minor ailments in British Columbia.
Pharmacists can choose a minor ailment condition, 
access a list of medications recommended for that specific condition, and prescribe them to patients.
In addition, pharmacists will be able to modify the list of conditions 
and associated medications stored in the application database. 
Pharmacists will also be able to view and modify the list of medications each patient is taking.

## Who will use it?
Any **pharmacist** in *British Columbia* who would like to prescribe for minor ailments.

## Why is this project of interest to you?
Pharmacist Prescribing Services rolled out in British Columbia in June 2023. 
As a pharmacist, I found it time-consuming to look through guidelines and resources on what conditions 
I am allowed to prescribe for and what medications to prescribe each time a patient presents at the pharmacy. 
I hope this project will increase the efficiency and decrease the workload of a pharmacist.

## User Stories
- As a user, I want to be able to **add** a condition to the database
- As a user, I want to be able to **add** a medication to a condition in the database
- As a user, I want to be able to **add** a side effect to a drug in the database
- As a user, I want to be able to **view** a list of all the conditions in the database
- As a user, I want to be able to **view** a list of all the conditions in the database in alphabetical order
- As a user, I want to be able to **view** a list of medications recommended for a selected condition
- As a user, I want to be able to **add** a patient to the list of patients in the database
- As a user, I want to be able to **view** a list of medications a patient is taking
- As a user, I want to be able to **add** a medication to a patient's list of medications by prescribing
- As a user, I want to be able to **view** a list of side effects of the prescribed medication
- As a user, I want to be able to **remove** a medication from a patient's list of medications
- As a user, I want to be able to **see** the number of patients taking a specific medication

- As a user, I want have the option to **save** all the new data added (new condition, drug, patient, etc) to file
- As a user, I want have the option to **load** the previously saved data from file when I restart the application

# Instructions for Grader
- You can go to the Database tab to add new conditions, add new drugs to a condition, and add new side effects to a drug
- You can go to the Database tab to view conditions in original order or in alphabetical order
- You can go to the Patient tab to search an existing patient by entering the patient's name
- You can go to the Patient tab to search a patient, view the patient's drug list, and delete drugs from the drug list
- You can locate my visual component by looking at the Home tab & the Patient tab
- You can save the state of my application by clicking the Save button in the Home tab
- You can reload the state of my application by clicking the Load button in the Home tab

## Note
- followed Teller App format for Phase 1 of this project
- modeled the JsonSerializationDemo format for Phase 2 of this project
- for Phase 3 GUI: referenced SmartHome: https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters
- learned Swing syntax from https://youtube.com/watch?v=Kmgo00avvEw
- learned JScrollPane from https://youtube.com/watch?v=OJSAnlzXRDk
- condition & drug list from BC Pharmacy Association MACS Resources website
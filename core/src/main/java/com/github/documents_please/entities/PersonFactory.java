package com.github.documents_please.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.github.documents_please.resources.Assets;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PersonFactory {
    private int maxSprites;
    private int maxStamps;
    private int day;
    private int month;
    private int year;

    public PersonFactory(int day, int month, int year) {
        maxSprites = Assets.idles.size - 1;
        maxStamps = Assets.stamps.size - 1;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    private String generateRandomDate(int y, int m, int d) {
        LocalDate startDate = LocalDate.of(year, month, day);
        LocalDate endDate = LocalDate.of(y, m, d);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = MathUtils.random(0, (int) daysBetween);
        return startDate.plusDays(randomDays).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    private String generateRandomDate(int year, int month, int day, int y, int m, int d) {
        LocalDate startDate = LocalDate.of(year, month, day);
        LocalDate endDate = LocalDate.of(y, m, d);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = MathUtils.random(0, (int) daysBetween);
        return startDate.plusDays(randomDays).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    private void makeInvalid(int key, Person person, String gender, int stampIdx, Texture stamp, String name, int nameIdx,
                             String surname, int surnameIdx, String address, String id, String age, String purpose,
                             String howLong, String disease, String validUntilPassport, String validUntilInvite,
                             String dateVaccination, int countryStampIdx, Texture addedStamp) {

        switch (key) {
            case 1:
                switch (MathUtils.random(0, 4)) {
                    case 0 -> {
                        if (gender.equals("жен")) {
                            person.setPassport(Assets.femaleNames.get((nameIdx + 1) % Assets.femaleNames.size()),
                                surname, address, id, age, gender, validUntilPassport, stamp);
                        }
                        else {
                            person.setPassport(Assets.maleNames.get((nameIdx + 1) % Assets.maleNames.size()),
                                surname, address, id, age, gender, validUntilPassport, stamp);
                        }
                    }

                    case 1 -> {
                        if (gender.equals("жен")) {
                            person.setPassport(name, Assets.femaleSurnames.get((surnameIdx + 1) % Assets.femaleSurnames.size()),
                                address, id, age, gender, validUntilPassport, stamp);
                        }
                        else {
                            person.setPassport(name, Assets.maleSurnames.get((surnameIdx + 1) % Assets.maleSurnames.size()),
                                address, id, age, gender, validUntilPassport, stamp);
                        }
                    }

                    case 2 -> {
                        person.setPassport(name, surname, address, id.substring(3, 5) + id.substring(0, 3),
                            age, gender, validUntilPassport, stamp);
                    }

                    case 3 -> {
                        if (gender.equals("жен")) {
                            person.setPassport(name, surname, address, id, age, "муж", validUntilPassport, stamp);
                        }
                        else {
                            person.setPassport(name, surname, address, id, age, "жен", validUntilPassport, stamp);
                        }
                    }
                    case 4 -> {
                        person.setPassport(name, surname, address, id, age, "муж",
                            validUntilPassport.substring(3, 5) + "-" + validUntilPassport.substring(0, 2) + validUntilPassport.substring(5), stamp);
                    }
                }
                break;

            case 2:
                switch (MathUtils.random(0, 5)) {
                    case 0 -> {
                        if (gender.equals("жен")) {
                            person.setInvite(Assets.femaleNames.get((nameIdx + 1) % Assets.femaleNames.size()),
                                gender, purpose, howLong, validUntilInvite, id, stamp, addedStamp);
                        }
                        else {
                            person.setInvite(Assets.maleNames.get((nameIdx + 1) % Assets.maleNames.size()),
                                gender, purpose, howLong, validUntilInvite, id, stamp, addedStamp);
                        }
                    }

                    case 1 -> {
                        if (gender.equals("жен")) {
                            person.setInvite(name, "муж", purpose, howLong, validUntilInvite, id, stamp, addedStamp);
                        }
                        else {
                            person.setInvite(name, "жен", purpose, howLong, validUntilInvite, id, stamp, addedStamp);
                        }
                    }

                    case 2 -> {
                        person.setInvite(name, gender, purpose, howLong,
                            validUntilInvite.substring(3, 5) + "-" + validUntilInvite.substring(0, 2) + validUntilInvite.substring(5), id, stamp, addedStamp);
                    }

                    case 3 -> {
                        person.setInvite(name, gender, purpose, howLong, validUntilInvite,
                            id.substring(3, 5) + id.substring(0, 3), stamp, addedStamp);
                    }

                    case 4 -> {
                        person.setInvite(name, gender, purpose, howLong, validUntilInvite, id, stamp,
                            Assets.stamps.get((countryStampIdx + MathUtils.random(1, Assets.stamps.size - 1)) % Assets.stamps.size));
                    }
                }
                break;

            case 3:
                switch (MathUtils.random(0, 4)) {
                    case 0 -> {
                        if (gender.equals("жен")) {
                            person.setVaccination(Assets.femaleNames.get((nameIdx + 1) % Assets.femaleNames.size()),
                                surname, disease, dateVaccination, stamp);
                        }
                        else {
                            person.setVaccination(Assets.maleNames.get((nameIdx + 1) % Assets.maleNames.size()),
                                surname, disease, dateVaccination, stamp);
                        }
                    }

                    case 1 -> {
                        if (gender.equals("жен")) {
                            person.setVaccination(name, Assets.femaleSurnames.get((surnameIdx + 1) % Assets.femaleSurnames.size()),
                                disease, dateVaccination, stamp);
                        }
                        else {
                            person.setVaccination(name, Assets.maleSurnames.get((surnameIdx + 1) % Assets.maleSurnames.size()),
                                disease, dateVaccination, stamp);
                        }
                    }

                    case 2 -> {
                        person.setVaccination(name, surname, disease,
                            generateRandomDate(year - 2, month, day, year - 1, month, day), stamp);
                    }

                    case 3 -> {
                        person.setVaccination(name, surname, disease, dateVaccination,
                            Assets.stamps.get((stampIdx + MathUtils.random(1, Assets.stamps.size - 1)) % Assets.stamps.size));
                    }
                }
                break;
        }
        person.setIsValid(false);
    }

    public Person getNewPerson(int countryStampIdx) {
        int randomKey = MathUtils.random(0, maxSprites);
        String gender;
        if (randomKey < (maxSprites + 1) / 2) gender = "жен";
        else gender = "муж";
        int stampIdx = MathUtils.random(0, maxStamps);
        Texture stamp = Assets.stamps.get(stampIdx);

        Person person = new Person(Assets.idles.get(randomKey), Assets.walkIns.get(randomKey),
                                    Assets.accepts.get(randomKey), Assets.declines.get(randomKey));

        String name = "";
        int nameIdx;
        String surname = "";
        int surnameIdx;
        String address = "";
        String id = "";
        String age = "";
        String purpose = "";
        String howLong = "";
        String disease = "";
        String validUntilPassport = "";
        String validUntilInvite = "";
        String dateVaccination = "";
        Texture addedStamp;

        if (gender.equals("жен")) {
            nameIdx = MathUtils.random(0, Assets.femaleNames.size() - 1);
            name = Assets.femaleNames.get(nameIdx);
            surnameIdx = MathUtils.random(0, Assets.femaleSurnames.size() - 1);
            surname = Assets.femaleSurnames.get(surnameIdx);
        }
        else {
            nameIdx = MathUtils.random(0, Assets.maleNames.size() - 1);
            name = Assets.maleNames.get(nameIdx);
            surnameIdx = MathUtils.random(0, Assets.maleSurnames.size() - 1);
            surname = Assets.maleSurnames.get(surnameIdx);
        }

        address = Assets.cities.get(MathUtils.random(0, Assets.cities.size() - 1));
        id = "" + MathUtils.random(10000, 99999);
        age = "" + MathUtils.random(19, 30);
        howLong = MathUtils.random(30, 90) + " дней";
        validUntilPassport = generateRandomDate(2030, 12, 31);
        validUntilInvite = generateRandomDate(year, 12, 31);
        purpose = Assets.purposes.get(MathUtils.random(0, Assets.purposes.size() - 1));
        disease = Assets.diseases.get(MathUtils.random(0, Assets.diseases.size() - 1));
        dateVaccination = generateRandomDate(year, Math.max(month - 3, 1), day, year, month, day);
        addedStamp = Assets.stamps.get(countryStampIdx);

        person.setPassport(name, surname, address, id, age, gender, validUntilPassport, stamp);
        person.setInvite(name, gender, purpose, howLong, validUntilInvite, id, stamp, addedStamp);
        person.setVaccination(name, surname, disease, dateVaccination, stamp);

        boolean isValid = (MathUtils.random(0, 100) > 40);
        if (!isValid) {
            for (int i = 1; i <= MathUtils.random(1, 3); i++) {
                makeInvalid(i, person, gender, stampIdx, stamp, name, nameIdx,surname, surnameIdx, address, id, age,
                    purpose, howLong, disease, validUntilPassport, validUntilInvite, dateVaccination, countryStampIdx, addedStamp);
            }
        }

        return person;
    }
}

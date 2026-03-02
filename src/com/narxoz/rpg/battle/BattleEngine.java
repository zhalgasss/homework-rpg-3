package com.narxoz.rpg.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleEngine {

    private static BattleEngine instance;
    private Random random = new Random();

    private BattleEngine() {
        System.out.println("BattleEngine initialized");
    }

    public static BattleEngine getInstance() {
        if (instance == null) {
            instance = new BattleEngine();
        }
        return instance;
    }

    public void setRandomSeed(long seed) {
        random.setSeed(seed);
    }

    public EncounterResult runEncounter(List<Combatant> heroes, List<Combatant> enemies) {

        List<String> battleLog = new ArrayList<>();
        int rounds = 0;

        while (hasAlive(heroes) && hasAlive(enemies)) {

            rounds++;
            battleLog.add("=== Round " + rounds + " ===");

            // Heroes attack
            for (Combatant hero : heroes) {
                if (hero.isAlive()) {

                    Combatant target = getRandomAlive(enemies);
                    if (target != null) {
                        int damage = hero.getAttackPower();
                        target.takeDamage(damage);

                        battleLog.add(hero.getName() +
                                " attacks " +
                                target.getName() +
                                " for " + damage);
                    }
                }
            }

            // Enemies attack
            for (Combatant enemy : enemies) {
                if (enemy.isAlive()) {

                    Combatant target = getRandomAlive(heroes);
                    if (target != null) {
                        int damage = enemy.getAttackPower();
                        target.takeDamage(damage);

                        battleLog.add(enemy.getName() +
                                " attacks " +
                                target.getName() +
                                " for " + damage);
                    }
                }
            }
        }

        String winner = hasAlive(heroes) ? "Heroes" : "Enemies";
        battleLog.add("Winner: " + winner);

        return new EncounterResult(winner, rounds, battleLog);
    }

    private boolean hasAlive(List<Combatant> list) {
        for (Combatant c : list) {
            if (c.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private Combatant getRandomAlive(List<Combatant> list) {

        List<Combatant> alive = new ArrayList<>();

        for (Combatant c : list) {
            if (c.isAlive()) {
                alive.add(c);
            }
        }

        if (alive.isEmpty()) {
            return null;
        }

        int index = random.nextInt(alive.size());
        return alive.get(index);
    }
}
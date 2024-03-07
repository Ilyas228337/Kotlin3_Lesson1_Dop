package Kotlinlesson2
import java.util.*

var bossHealth: Int = 900
var bossDamage: Int = ((10.0 / 100.0) * bossHealth).toInt()
var bossImmunityType: String? = null
var heroes: Array<String> = arrayOf("Swordsman", "Magic", "Archer", "Medic")
var heroesHealth: IntArray = intArrayOf(330, 290, 300, 280)
var heroesDamage: IntArray = intArrayOf(50, 70, 60, 0)

fun main() {
    printStatistics(0)
    var currentRound = 0
    while (!isGameFinished) {
        currentRound++
        setBossImmunityType()
        round(currentRound)
    }
}

fun setBossImmunityType() {
    val random = Random()
    val randomHeroIndex = random.nextInt(heroes.size - 1)
    bossImmunityType = heroes[randomHeroIndex]
    println("Boss have immunity to " + bossImmunityType)
}

val isGameFinished: Boolean
    get() {
        if (bossHealth <= 0) {
            println("Heroes won!!!")
            return true
        }

        var allHeroesDead = false

        for (heroHealth in heroesHealth) {
            if (heroHealth <= 0) {
                allHeroesDead = true
                break
            }
        }
        if (allHeroesDead) {
            println("Boss won!!!")
        }
        return allHeroesDead
    }

fun printStatistics(round: Int) {
    println("_________________________________________")
    println("--------------- + Round $round + ---------------")
    println("Boss health: [" + bossHealth + "] Damage: [" + bossDamage + "]")

    for (i in heroes.indices) {
        println(heroes[i] + " health: [" + heroesHealth[i] + "] Damage: [" + heroesDamage[i] + "]")
    }

    println("_________________________________________")
}

fun round(round: Int) {
    heroesHit()
    bossHit()
    printStatistics(round)
}

fun heroesHit() {
    for (i in 0 until heroes.size - 1) {
        if (heroesHealth[i] > 0 && bossHealth > 0) {
            if (heroesHealth[i] <= 100) {
                val random = Random()
                val hillHeroes = random.nextInt(100)
                if (hillHeroes > 50) {
                    println("Medic has hill [" + heroes[i] + "] for [" + hillHeroes + "]")
                    heroesHealth[i] += hillHeroes
                    break
                }
            }
            if (bossImmunityType !== heroes[i]) {
                val random = Random()
                val criticalChance = random.nextInt(5) // 0, 1, 2, 3, 4
                val coefic = random.nextInt(4) + 2 // 2, 3, 4, 5
                if (criticalChance > 3) {
                    val criticalDamage = heroesDamage[i] * coefic
                    if (bossHealth - criticalDamage > 0) {
                        bossHealth = bossHealth - criticalDamage
                        println(heroes[i] + " use critical damage! Damage: [" + criticalDamage + "]")
                    } else {
                        bossHealth = 0
                    }
                } else {
                    if (bossHealth - heroesDamage[i] > 0) {
                        bossHealth = bossHealth - heroesDamage[i]
                    } else {
                        bossHealth = 0
                    }
                }
            }
        } else {
            bossHealth = 0
        }
    }
}

fun bossHit() {
    for (i in heroesHealth.indices) {
        if (heroesHealth[i] > 0 && bossHealth > 0) {
            if (heroesHealth[i] - bossDamage < 0) {
                heroesHealth[i] = 0
            } else {
                heroesHealth[i] = heroesHealth[i] - bossDamage
            }
        }
    }
}
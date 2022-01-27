package com.example.draftmakerhelper.models

class InvestigatorCardModel {
        pack_code: string;
        pack_name: string;
        type_code: string;
        type_name: string;
        faction_code: string;
        faction_name: string;
        position: number;
        exceptional: boolean;
        myriad: boolean;
        code: string;
        name: string;
        real_name: string;
        subname: string;
        text: string;
        real_text: string;
        quantity: number;
        skill_willpower: number;
        skill_intellect: number;
        skill_combat: number;
        skill_agility: number;
        health: number;
        health_per_investigator: boolean;
        sanity: number;
        deck_limit: number;
        traits: string;
        real_traits: string;
        deck_requirements: DeckRequirements;
        deck_options?: (DeckOptionsEntity)[] | null;
        flavor: string;
        illustrator: string;
        is_unique: boolean;
        permanent: boolean;
        double_sided: boolean;
        back_text: string;
        back_flavor: string;
        octgn_id: string;
        url: string;
        imagesrc: string;
        backimagesrc: string;
    }

}
    interface DeckRequirements {
        size: number;
        card: Card;
        random?: (RandomEntity)[] | null;
    }
    interface Card {
        08002: 08002;
        08003: 08003;
    }
    interface 08002 {
        08002: string;
    }
    interface 08003 {
        08003: string;
    }
    interface RandomEntity {
        target: string;
        value: string;
    }
    interface DeckOptionsEntity {
        faction?: (string)[] | null;
        level: Level;
        limit?: number | null;
        error?: string | null;
    }
    interface Level {
        min: number;
        max: number;
    }
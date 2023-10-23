import Chance from 'chance';
import * as model from './form-value-model.cy';
const chance = new Chance();

let generateZero = (): model.Zero => {
  return {
    name: chance.string({ length: 5 }),
    surname: chance.string({ length: 5 }),
    prefix: chance.integer({ min: 1, max: 166 }),
    phoneNumber: chance.integer({ min: 1, max: 10000000 }),
  };
};
let generateOne = (): model.One => {
  return {
    burialType: chance.integer({ min: 0, max: 2 }),
    otherBurial: chance.string({ length: 25 }),
    cremationType: chance.integer({ min: 0, max: 4 }),
    burialPlace: chance.string({ length: 20 }),
    graveInscription: chance.string({ length: 25 }),
    ifGraveInscription: chance.bool(),
    openCoffin: chance.bool(),
    clothes: chance.string({ length: 10 }),
  };
};
let generateTwo = (): model.Two => {
  return {
    ifFlowers: chance.integer({ min: 0, max: 1 }),
    flowers: chance.string({ length: 7 }),
    placeOfCeremony: chance.string({ length: 7 }),
    musicType: chance.integer({ min: 0, max: 3 }),
    bandName: chance.string({ length: 5 }),
    ifGuests: chance.integer({ min: 0, max: 1 }),
    guests: chance.string({ length: 20 }),
    notInvited: chance.string({ length: 50 }),
  };
};
let generateThree = (): model.Three => {
  return {
    ifFarewellLetter: chance.integer({ min: 0, max: 1 }),
    farewellReader: chance.string({ length: 5 }),
    ifVideoSpeech: chance.integer({ min: 0, max: 1 }),
    ifTestament: chance.integer({ min: 0, max: 1 }),
    ifOther4: chance.integer({ min: 0, max: 1 }),
    other: chance.string({ length: 5 }),
  };
};
let generateRelatives = (): model.relative[] => {
  let relative: model.relative[] = [];
  for (let i = 0; i < chance.integer({ min: 1, max: 5 }); i++) {
    relative.push({ name: chance.string({ length: 5 }), phone: chance.string({ length: 5 }), email: chance.string({ length: 5 }) });
  }
  return relative;
};

export let generateFormValues = (): model.form => {
  return { zero: generateZero(), one: generateOne(), two: generateTwo(), three: generateThree(), relative: generateRelatives() };
};

export let lastMessageText = {
  farewellLetter: {
    yes: "Here's file with your farewell letter: File",
    no: "You didn't attach your farewell letter, edit your profile to change it",
  },
  testament: {
    yes: "Here's file with your will: File",
    no: "You didn't attach your will, edit your profile to change it",
  },
  videoSpeech: {
    yes: "Here's file with your viceo speech: File",
    no: "You didn't attach your video speech, edit your profile to change it",
  },
  other4: {
    yes: 'Here are your other requests',
    no: "You don't have any other requests regarding your passing",
  },
};

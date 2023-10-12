import Chance from 'chance';

const chance = new Chance();

let generateZero = (): Zero => {
  return {
    name: chance.string({ length: 5 }),
    surname: chance.string({ length: 5 }),
    prefix: chance.integer({ min: 1, max: 166 }),
    phoneNumber: chance.integer({ min: 1, max: 10000000 }),
  };
};
let generateOne = (): One => {
  return {
    burialType: chance.integer({ min: 0, max: 2 }),
    otherBurial: chance.string({ length: 25 }),
    cremationType: chance.integer({ min: 0, max: 3 }),
    burialPlace: chance.string({ length: 20 }),
    graveInscription: chance.string({ length: 25 }),
    clothes: chance.string({ length: 10 }),
  };
};
let generateTwo = (): Two => {
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
let generateThree = (): Three => {
  return {
    ifFarewellLetter: chance.integer({ min: 0, max: 1 }),
    farewellReader: chance.string({ length: 5 }),
    ifVideoSpeech: chance.integer({ min: 0, max: 1 }),
    ifTestament: chance.integer({ min: 0, max: 1 }),
    ifOther4: chance.integer({ min: 0, max: 1 }),
    other: chance.string({ length: 5 }),
  };
};
let generateRelatives = (): relative[] => {
  let relative: relative[] = [];
  for (let i = 0; i < chance.integer({ min: 1, max: 5 }); i++) {
    relative.push({ name: chance.string({ length: 5 }), phone: chance.string({ length: 5 }), email: chance.string({ length: 5 }) });
  }
  return relative;
};

interface Zero {
  name: string;
  surname: string;
  prefix: number;
  phoneNumber: number;
}

interface One {
  burialType: number;
  otherBurial: string;
  cremationType: number;
  burialPlace: string;
  graveInscription: string;
  clothes: string;
}

interface Two {
  ifFlowers: number;
  flowers: string;
  placeOfCeremony: string;
  musicType: number;
  bandName: string;
  ifGuests: number;
  guests: string;
  notInvited: string;
}

interface Three {
  ifFarewellLetter: number;
  farewellReader: string;
  ifVideoSpeech: number;
  ifTestament: number;
  ifOther4: number;
  other: string;
}

interface relative {
  name: string;
  email: string;
  phone: string;
}

export interface form {
  zero: Zero;
  one: One;
  two: Two;
  three: Three;
  relative: relative[];
}

export let generateFormValues = (): form => {
  return { zero: generateZero(), one: generateOne(), two: generateTwo(), three: generateThree(), relative: generateRelatives() };
};

export let login = (login: string, password: string) => {
  cy.visit('/');
  cy.get('#bar6')
    .click()
    .get('#username')
    .type(login, { scrollBehavior: 'center' })
    .get('#password')
    .type(password, { scrollBehavior: 'center' })
    .get('.login-button')
    .click({ scrollBehavior: 'center' });
};

export let zeroForm = (zero: Zero) => {
  cy.get('#firstName')
    .type(zero.name, { scrollBehavior: 'center' })
    .get('#lastName')
    .type(zero.surname, { scrollBehavior: 'center' })
    .get('#prefix')
    .type(zero.prefix.toString(), { scrollBehavior: 'center' })
    .get('#phone')
    .type(zero.phoneNumber.toString(), { scrollBehavior: 'center' })
    .get('.profileForm-button')
    .first()
    .click({ scrollBehavior: 'center' });
};

export let skipPicture = () => {
  cy.get('.skip').click({ scrollBehavior: 'center' }).get('button').click({ scrollBehavior: 'center' });
};

export let oneForm = (one: One) => {
  getBurial(one.burialType, one);
  cy.get('button[type=submit]').click({ scrollBehavior: 'center' });
};

export let secondForm = (two: Two) => {
  getIfInput('flowers', 'ifFlowers', two.flowers, two.ifFlowers);
  cy.get('input[name=placeOfCeremony]').type(two.placeOfCeremony, { scrollBehavior: 'center' });
  getBandName(two.musicType, two.bandName);
  getIfInput('ifGuests', 'guests', two.guests, two.ifGuests, two.notInvited);
  cy.get('button[type=submit]').click({ scrollBehavior: 'center' });
};

export let thirdForm = (three: Three) => {
  getIfInput('ifFarewellLetter', 'farewellReader', three.farewellReader, three.ifFarewellLetter);

  cy.get('input[name=ifVideoSpeech]')
    .eq(three.ifVideoSpeech)
    .click({ scrollBehavior: 'center' })
    .get('input[name=ifTestament]')
    .eq(three.ifTestament)
    .click({ scrollBehavior: 'center' });
  getIfInput('ifOther4', 'other', three.other, three.ifOther4);
  cy.get('button[type=submit]').click({ scrollBehavior: 'center' });
  console.log(three);
};

export let fourthForm = (relatives: relative[]) => {
  for (let relative of relatives) {
    cy.get('#name')
      .type(relative.name, { scrollBehavior: 'center' })
      .get('#email')
      .type(relative.email, { scrollBehavior: 'center' })
      .get('input[name=phone]')
      .type(relative.phone, { scrollBehavior: 'center' })
      .get('button[type=submit]')
      .click({ scrollBehavior: 'center' });
  }
  cy.get('button[type=button]').click({ scrollBehavior: 'center' });
};

let getIfInput = (radioName: string, inputName: string, value, int: number, guestsValue = '') => {
  if (int === 0) {
    if (radioName === 'ifGuests') {
      cy.get(`input[name=${radioName}]`).eq(int).click({ scrollBehavior: 'center' });
      ifGuests(value, guestsValue);
    } else {
      cy.get(`input[name=${radioName}]`)
        .eq(int)
        .click({ scrollBehavior: 'center' })
        .get(`input[name=${inputName}]`)
        .type(value, { scrollBehavior: 'center' });
    }
  } else {
    cy.get(`input[name=${radioName}]`).eq(int).click({ scrollBehavior: 'center' });
  }
};

let ifGuests = (guests: string, notInvited: string) => {
  cy.get('input[name=guests]')
    .type(guests, { scrollBehavior: 'center' })
    .get('input[name=notInvited]')
    .type(notInvited, { scrollBehavior: 'center' });
};

let getBandName = (number: number, value: string) => {
  switch (number) {
    case 0 || 1 || 3:
      cy.get('input[name=musicType]')
        .eq(number)
        .click({ scrollBehavior: 'center' })
        .get('input[name=spotify]')
        .type(value, { scrollBehavior: 'center' });
      break;
    case 2:
      cy.get('input[name=musicType]').eq(number).click({ scrollBehavior: 'center' });
      break;
  }
};

let getBurial = (number: number, one: One) => {
  switch (number) {
    case 0:
      cy.get('input[name=burialType]').eq(number).click({ scrollBehavior: 'center' });
      burialHappyPath(one);
      break;
    case 1:
      cy.get('input[name=burialType]').eq(number).click({ scrollBehavior: 'center' });
      handleCremation(one);
      break;
    case 2:
      cy.get('input[name=burialType]')
        .eq(number)
        .click({ scrollBehavior: 'center' })
        .get('input[name=burialMethod]')
        .type(one.otherBurial, { scrollBehavior: 'center' });
  }
};

let checkBurial = () => {
  cy.get('');
};

let burialHappyPath = (one: One) => {
  cy.get('#burialPlace')
    .type(one.burialPlace, { scrollBehavior: 'center' })
    .get('input[name=ifGraveInscription]')
    .first()
    .click({ scrollBehavior: 'center' })
    .get('#graveInscription')
    .type(one.graveInscription, { scrollBehavior: 'center' })
    .get('input[name=ifPhotoGrave]')
    .first()
    .click({ scrollBehavior: 'center' })
    .get('input[name=openCoffin]')
    .first()
    .click({ scrollBehavior: 'center' })
    .get('#clothes')
    .type(one.clothes, { scrollBehavior: 'center' });
};

let handleCremation = (one: One) => {
  switch (one.cremationType) {
    case 2:
      cy.get('input[name=burialMethod]').eq(one.cremationType).click({ scrollBehavior: 'center' });
      burialHappyPath(one);
      break;
    case 0 || 1 || 3:
      cy.get('input[name=burialMethod]').eq(one.cremationType).click({ scrollBehavior: 'center' });
      break;
    case 4:
      cy.get('input[name=burialMethod]')
        .eq(one.cremationType)
        .click({ scrollBehavior: 'center' })
        .get('input[type=text]')
        .type(one.otherBurial, { scrollBehavior: 'center' });
  }
};

import * as model from './form-value-model.cy';

export let zeroForm = (zero: model.Zero) => {
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

export let oneForm = (one: model.One) => {
  getBurial(one.burialType, one);
};

export let secondForm = (two: model.Two) => {
  getIfInput('flowers', 'ifFlowers', two.flowers, two.ifFlowers);
  cy.get('input[name=placeOfCeremony]').type(two.placeOfCeremony, { scrollBehavior: 'center' });
  getBandName(two.musicType, two.bandName);
  getIfInput('ifGuests', 'guests', two.guests, two.ifGuests, two.notInvited);
};

export let thirdForm = (three: model.Three) => {
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

export let fourthForm = (relatives: model.relative[]) => {
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
    case 0:
      cy.get('input[name=musicType]')
        .eq(number)
        .click({ scrollBehavior: 'center' })
        .get('input[name=spotify]')
        .type(value, { scrollBehavior: 'center' });
      break;
    case 1:
      cy.get('input[name=musicType]')
        .eq(number)
        .click({ scrollBehavior: 'center' })
        .get('input[name=spotify]')
        .type(value, { scrollBehavior: 'center' });
      break;
    case 3:
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

let burialHappyPath = (one: model.One) => {
  cy.get('#burialPlace').type(one.burialPlace, { scrollBehavior: 'center' });
  if (one.ifGraveInscription) {
    cy.get('input[name=ifGraveInscription]')
      .first()
      .click({ scrollBehavior: 'center' })
      .get('#graveInscription')
      .type(one.graveInscription, { scrollBehavior: 'center' });
  } else {
    cy.get('input[name=ifGraveInscription]').eq(1).click({ scrollBehavior: 'center' });
  }

  cy.get('input[name=ifPhotoGrave]').first().click({ scrollBehavior: 'center' });
  if (one.openCoffin) {
    cy.get('input[name=openCoffin]')
      .first()
      .click({ scrollBehavior: 'center' })
      .get('#clothes')
      .type(one.clothes, { scrollBehavior: 'center' });
  } else {
    cy.get('input[name=openCoffin]').eq(1).click({ scrollBehavior: 'center' });
  }
};

let handleCremation = (one: model.One) => {
  switch (one.cremationType) {
    case 2:
      cy.get('input[name=burialMethod]').eq(one.cremationType).click({ scrollBehavior: 'center' });
      burialHappyPath(one);
      break;
    case 4:
      cy.get('input[name=otherCremationInput]')
        .click({ scrollBehavior: 'center' })
        .get('.profileForm-input')
        .type(one.otherBurial, { scrollBehavior: 'center' });
      break;
    default:
      cy.get('input[name=burialMethod]').eq(one.cremationType).click({ scrollBehavior: 'center' });
      break;
  }
};

let getBurial = (number: number, one: model.One) => {
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

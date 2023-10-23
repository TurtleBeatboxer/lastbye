import * as model from './form-value-model.cy';
import * as help from './helper-functions.cy';
export let checkFuneral = (two: model.Two) => {
  checkFlowers(two.ifFlowers, two.flowers);
  checkFarewellCeremony(two.placeOfCeremony);
  checkMusicType(help.getMusicType(two.musicType), two.bandName);
  checkGuests(two.ifGuests, two.guests, two.notInvited);
};

let checkFlowers = (ifFlowers: number, flowers: string) => {
  if (ifFlowers === 0) {
    cy.get('.flowers').should('contain', 'For your last day you want to have this type of flower').should('contain', flowers);
  } else {
    cy.get('.flowers')
      .scrollIntoView({ offset: { top: -150, left: 50 } })
      .should('contain', 'You dont')
      .should('contain', "wont't any flowrs");
  }
};

let checkFarewellCeremony = (placeOfCeremony: string) => {
  cy.get('#placeOfCeremony').should('contain', 'You want your farewell ceremony to take place in/at').should('contain', placeOfCeremony);
};

let checkMusicType = (musicType: string, value: string) => {
  switch (musicType) {
    case 'playlist':
      cy.get('#playlist')
        .should('contain', "You want your favorite playlist to be played on your ceremony, here's link to")
        .should('contain', 'your playlist')
        .should('contain', value);
      break;
    case 'live':
      cy.get('#live').should('contain', 'You want your favorite band to be play on your ceremony, which is').should('contain', value);
      break;
    case 'noMusic':
      cy.get('#noMusic').should('contain', 'You want your ceremony to be silent, therefore no music');
      break;
    case 'other':
      cy.get('#otherMusic').should('contain', 'You have your own idea for music here it is').should('contain', value);
      break;
  }
};

let checkGuests = (ifGuests: number, guests: string, notInvited: string) => {
  if (ifGuests === 0) {
    cy.get('#guests')
      .should('contain', 'This is your list of guests')
      .get('#invitedGuests')
      .should('contain', 'People you would like to invite')
      .should('contain', guests)
      .get('#notInvited')
      .should('contain', notInvited);
  } else {
    cy.get('#guests').should('contain', "You don't want any guests");
  }
};

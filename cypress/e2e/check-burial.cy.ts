import * as model from './form-value-model.cy';
import * as help from './helper-functions.cy';

export let checkBurial = (one: model.One) => {
  cy.get('#edit').click({ scrollBehavior: 'center' });
  switch (one.burialType) {
    case 0:
      checkCoffinBurial(one);
      break;
    case 1:
      checkCremationBurial(one);
      break;
    case 2:
      checkOtherBurial(one);
      break;
  }
};

let checkCoffinBurial = (one: model.One) => {
  console.info(one);
  cy.get('.burial-method')
    .should('have.text', 'Your desired burial method is ' + help.convertBurialTypeToText(one.burialType))
    .get('.burial-place')
    .should('have.text', 'You want your burial place to be ' + one.burialPlace);

  if (one.ifGraveInscription) {
    cy.get('.ifGraveInscription')
      .should('contain', 'Grave insription:')
      .should('contain', 'You want to have grave inscription')
      .get('.graveInscription')
      .should('contain', 'You want your grave inscription to be:')
      .should('contain', one.graveInscription);
  } else {
    cy.get('.ifGraveInscription').should('contain', 'Grave insription:').should('contain', "You don't want");
  }
  if (one.openCoffin) {
    cy.get('.openCoffin')
      .should('contain', 'Is your casket going to be open:')
      .should('contain', 'yes')
      .get('.clothes')
      .should('contain', 'You want to be buried in')
      .should('contain', one.clothes);
  } else {
    cy.get('.openCoffin').should('contain', 'Is your casket going to be open:').should('contain', 'no');
  }
};

let checkCremationBurial = (one: model.One) => {
  console.info(one.cremationType + ' cremation');
  if (one.cremationType === 2) {
    cy.get('.burial-method')
      .should('have.text', 'Your desired burial method is ' + help.getCremationType(one.cremationType))
      .get('.burial-place')
      .should('have.text', 'You want your burial place to be ' + one.burialPlace);

    if (one.ifGraveInscription) {
      cy.get('.ifGraveInscription')
        .should('contain', 'Grave insription:')
        .should('contain', 'You want to have grave inscription')
        .get('.graveInscription')
        .should('contain', 'You want your grave inscription to be:')
        .should('contain', one.graveInscription);
    } else {
      cy.get('.ifGraveInscription').should('contain', 'Grave insription:').should('contain', "You don't want to have grave");
    }
  } else if (one.cremationType === 4) {
    cy.get('.other-cremation').should('contain', 'You want to be cremated and you have your own idea').should('contain', one.otherBurial);
  } else {
    cy.get('.cremation').should('contain', 'You want to be cremated and').should('contain', help.getCremationType(one.cremationType));
  }
};

let checkOtherBurial = (one: model.One) => {
  cy.get('.other-burial').should('contain', 'Your vision of burial:').should('contain', one.otherBurial);
};

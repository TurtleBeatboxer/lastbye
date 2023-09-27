import Chance from 'chance';
const chance = new Chance();

let values: values = { name: '', surname: '', prefix: 0, phoneNumber: 0 };
let profileFormOne: profileFormOne = { burialPlace: '', graveInscription: '', clothes: '' };
let generateValuesforOne = () => {
  profileFormOne.burialPlace = chance.string({ length: 20 });
  profileFormOne.graveInscription = chance.string({ length: 25 });
  profileFormOne.clothes = chance.string({ length: 10 });
};
let generateValues = () => {
  (values.name = chance.string({ length: 5 })),
    (values.surname = chance.string({ length: 5 })),
    (values.prefix = chance.integer({ min: 1, max: 166 }));
  values.phoneNumber = chance.integer({ min: 1, max: 10000000 });
};
generateValues();
generateValuesforOne();
describe('My First Test', () => {
  beforeEach(() => {});
  it('should login from main page', () => {
    cy.visit('/');
    cy.get('#bar6')
      .click()
      .get('#username')
      .type('user', { scrollBehavior: 'center' })
      .get('#password')
      .type('user', { scrollBehavior: 'center' })
      .get('.login-button')
      .click({ scrollBehavior: 'center' });
  });

  it('should register zero form', () => {
    login();
    zeroForm();
    skipPicture();
    oneForm();
  });
});

let login = () => {
  cy.visit('/');
  cy.get('#bar6')
    .click()
    .get('#username')
    .type('user', { scrollBehavior: 'center' })
    .get('#password')
    .type('user', { scrollBehavior: 'center' })
    .get('.login-button')
    .click({ scrollBehavior: 'center' });
};

let zeroForm = () => {
  cy.get('#firstName')
    .type(values.name, { scrollBehavior: 'center' })
    .get('#lastName')
    .type(values.surname, { scrollBehavior: 'center' })
    .get('#prefix')
    .type(values.prefix.toString(), { scrollBehavior: 'center' })
    .get('#phone')
    .type(values.phoneNumber.toString(), { scrollBehavior: 'center' })
    .get('.profileForm-button')
    .first()
    .click({ scrollBehavior: 'center' });
};

let skipPicture = () => {
  cy.get('.skip').click({ scrollBehavior: 'center' }).get('button').click({ scrollBehavior: 'center' });
};

let oneForm = () => {
  cy.get('input[value=coffin]')
    .click({ scrollBehavior: 'center' })
    .get('#burialPlace')
    .type(profileFormOne.burialPlace, { scrollBehavior: 'center' })
    .get('input[name=ifGraveInscription]')
    .first()
    .click({ scrollBehavior: 'center' })
    .get('#graveInscription')
    .type(profileFormOne.graveInscription, { scrollBehavior: 'center' })
    .get('input[name=ifPhotoGrave]')
    .first()
    .click({ scrollBehavior: 'center' })
    .get('input[name=openCoffin]')
    .first()
    .click({ scrollBehavior: 'center' })
    .get('#clothes')
    .type(profileFormOne.clothes, { scrollBehavior: 'center' })
    .get('button[type=submit]')
    .click({ scrollBehavior: 'center' });
};

interface values {
  name: string;
  surname: string;
  prefix: number;
  phoneNumber: number;
}

interface profileFormOne {
  burialPlace: string;
  graveInscription: string;
  clothes: string;
}

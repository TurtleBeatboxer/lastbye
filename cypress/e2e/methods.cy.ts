import Chance from 'chance';
import * as model from './form-value-model.cy';
const chance = new Chance();

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

export let startEdit = () => {
  cy.get('#edit').click({ scrollBehavior: 'center' }).get('#editButton').click({ scrollBehavior: 'center' });
};

const chance = new Chance();
import * as methods from './methods.cy';
import { from } from 'rxjs';

let form: methods.form = methods.generateFormValues();

describe('My First Test', () => {
  beforeEach(() => {
    cy.viewport(1920, 1080);
  });
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
    methods.login('user', 'user');
    methods.zeroForm(form.zero);
    methods.skipPicture();
    methods.oneForm(form.one);
    methods.secondForm(form.two);
    methods.thirdForm(form.three);
    methods.fourthForm(form.relative);
  });
});

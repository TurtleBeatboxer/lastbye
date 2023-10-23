import * as methods from './methods.cy';
import * as generate from './generate-values.cy';
import * as model from './form-value-model.cy';
import * as register from './register-form.cy';
import * as burial from './check-burial.cy';
import * as funeral from './check-funeral.cy';
import * as lastMessage from './check-last-message.cy';
let form: model.form = generate.generateFormValues();

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
    methods.login('user4', 'user');
    register.zeroForm(form.zero);
    register.skipPicture();

    register.oneForm(form.one);
    cy.get('button[type=submit]').click({ scrollBehavior: 'center' });
    register.secondForm(form.two);

    cy.get('button[type=submit]').click({ scrollBehavior: 'center' });
    register.thirdForm(form.three);
    register.fourthForm(form.relative);
    cy.get('#edit').click({ scrollBehavior: 'center' });
  });
  it('should chcek burial', () => {
    methods.login('user4', 'user');
    cy.get('#edit').click({ scrollBehavior: 'center' });
    burial.checkBurial(form.one);
    funeral.checkFuneral(form.two);
    lastMessage.checkLastMesssage(form.three);
  });

  it('should edit form', () => {
    methods.login('user4', 'user');
    form = generate.generateFormValues();
    methods.startEdit();
    cy.get('#burialEdit').click({ scrollBehavior: 'center' });
    register.oneForm(form.one);
    cy.get('button[type=submit]').click({ scrollBehavior: 'center' }).get('#editButton').click({ scrollBehavior: 'center' });
    cy.get('#funeralEdit').click({ scrollBehavior: 'center' });
    register.secondForm(form.two);
    cy.get('button[type=submit]').click({ scrollBehavior: 'center' }).get('#editButton').click({ scrollBehavior: 'center' });
    cy.get('#messageEdit').click({ scrollBehavior: 'center' });

    register.thirdForm(form.three);
    cy.get('.logout').click({ scrollBehavior: 'center' });
    methods.login('user4', 'user');
    cy.get('#edit').click({ scrollBehavior: 'center' });
    burial.checkBurial(form.one);
    funeral.checkFuneral(form.two);
    lastMessage.checkLastMesssage(form.three);
  });
});

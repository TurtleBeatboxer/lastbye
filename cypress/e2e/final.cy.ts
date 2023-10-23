import * as methods from './methods.cy';
import * as generate from './generate-values.cy';
import * as model from './form-value-model.cy';
import * as register from './register-form.cy';
import * as burial from './check-burial.cy';
import * as funeral from './check-funeral.cy';
import * as lastMessage from './check-last-message.cy';
let numberOfTest = 5;

let generateForms = (number: number) => {
  let result: model.form[] = [];
  for (let i = 0; i < number; i++) {
    result.push(generate.generateFormValues());
  }
  return result;
};

let forms: model.form[] = generateForms(numberOfTest);

describe('Final tests', () => {
  beforeEach(() => {
    cy.viewport(1920, 1080);
  });

  it('should througly check form ten times', () => {
    for (let i = 0; i < numberOfTest; i++) {
      console.info(forms[i]);
      methods.login(`user${i + 1}`, 'user');
      register.zeroForm(forms[i].zero);
      register.skipPicture();
      register.oneForm(forms[i].one);
      cy.get('button[type=submit]').click({ scrollBehavior: 'center' });
      register.secondForm(forms[i].two);
      cy.get('button[type=submit]').click({ scrollBehavior: 'center' });
      register.thirdForm(forms[i].three);
      register.fourthForm(forms[i].relative);
      cy.get('.logout').click({ scrollBehavior: false });
    }
  });

  it('should chcek burial', () => {
    for (let i = 0; i < numberOfTest; i++) {
      methods.login(`user${i + 1}`, 'user');
      cy.get('#edit').click({ scrollBehavior: 'center' });
      burial.checkBurial(forms[i].one);
      funeral.checkFuneral(forms[i].two);
      lastMessage.checkLastMesssage(forms[i].three);
      cy.get('.logout').click({ scrollBehavior: 'center' });
    }
  });

  it('should check ten burial places', () => {
    for (let i = 0; i < numberOfTest; i++) {
      methods.login(`user${i + 1}`, 'user');
      forms = generateForms(10);
      methods.startEdit();
      cy.get('#burialEdit').click({ scrollBehavior: 'center' });
      register.oneForm(forms[i].one);
      cy.get('button[type=submit]').click({ scrollBehavior: 'center' }).get('#editButton').click({ scrollBehavior: 'center' });
      cy.get('#funeralEdit').click({ scrollBehavior: 'center' });
      register.secondForm(forms[i].two);
      cy.get('button[type=submit]').click({ scrollBehavior: 'center' }).get('#editButton').click({ scrollBehavior: 'center' });
      cy.get('#messageEdit').click({ scrollBehavior: 'center' });

      register.thirdForm(forms[i].three);
      cy.get('.logout').click({ scrollBehavior: 'center' });
      methods.login(`user${i + 1}`, 'user');
      cy.get('#edit').click({ scrollBehavior: 'center' });
      burial.checkBurial(forms[i].one);
      funeral.checkFuneral(forms[i].two);
      lastMessage.checkLastMesssage(forms[i].three);
      cy.get('.logout').click({ scrollBehavior: 'center' });
    }
  });
});

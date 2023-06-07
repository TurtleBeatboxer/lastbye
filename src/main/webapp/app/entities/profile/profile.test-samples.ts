import { IProfile, NewProfile } from './profile.model';

export const sampleWithRequiredData: IProfile = {
  id: 11865,
};

export const sampleWithPartialData: IProfile = {
  id: 70575,
  surname: 'copy Customer Handmade',
  phone: 27116,
  prefix: 'users',
  burialMethod: 'software up RAM',
  clothes: 'Assistant solutions',
  placeOfCeremony: 'Avon Table',
  spotify: 'withdrawal Sleek tan',
  ifFlowers: 'Applications envisioneer',
  farewellLetter: 'system-worthy action-items',
  speech: 'Awesome auxiliary synergies',
  testament: 'Factors Borders',
  other: 'Licensed bypass',
  codeQR: 'overriding radical',
};

export const sampleWithFullData: IProfile = {
  id: 87355,
  surname: 'frame multi-byte',
  phone: 96716,
  prefix: 'Steel sky',
  burialMethod: 'uniform tangible Practical',
  clothes: 'process',
  placeOfCeremony: 'Account',
  photo: 'connecting card Buckinghamshire',
  graveInscription: 'user-centric Ireland program',
  spotify: 'Steel Rubber Supervisor',
  guests: 'up',
  notInvited: 'withdrawal',
  obituary: 'generate',
  purchasedPlace: false,
  ifPurchasedOther: 'holistic convergence Rustic',
  flowers: false,
  ifFlowers: 'online Dynamic generate',
  farewellLetter: 'Guyana ivory Research',
  speech: 'group success Bike',
  videoSpeech: 'Drive Borders',
  testament: 'Frozen cross-platform',
  accessesForRelatives: 'National',
  other: 'Orchestrator Bacon',
  codeQR: 'Unbranded Electronics',
};

export const sampleWithNewData: NewProfile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

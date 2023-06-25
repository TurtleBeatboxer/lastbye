interface requestData {
  userId: number;
  levelOfForm: number;
  login: string;
}

export interface profileFormData1 extends Partial<requestData> {
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  prefix: number;
  phoneNumber: number;
}

export interface profileFormData2 extends Partial<requestData> {
  burialMethod: string;
  graveInscription: string;
  openCoffin: boolean;
  clothes: string;
}

export interface profileFormData3 extends Partial<requestData> {
  flowers: boolean;
  ifFlowers: string;
  obituary: string;
  spotify: string;
  guests: string;
  notInvited: string;
  placeOfCeremony: string;
}

export interface profileFormData4 extends Partial<requestData> {
  farewellLetter: string;
  videoSpeech: string;
  testament: string;
  other: string;
}

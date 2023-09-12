interface requestData {
  userId: number;
  levelOfForm: number;
  login: string;
}

export interface profileFormData1 extends Partial<requestData> {
  firstName: string;
  lastName: string;
  prefix: string;
  phone: number;
}

export interface profileFormData2 extends Partial<requestData> {
  burialMethod: string;
  burialPlace: string;
  graveInscription: string;
  openCoffin: boolean | null;
  clothes: string;
}

export interface profileFormData3 extends Partial<requestData> {
  flowers: boolean | null;
  ifFlowers: string;
  // obituary: string;
  spotify: string;
  guests: string;
  notInvited: string;
  placeOfCeremony: string;
}

export interface profileFormData4 extends Partial<requestData> {
  farewellLetter: boolean | null;
  videoSpeech: boolean | null;
  testament: boolean | null;
  other: string;
}

export class Relative {
  constructor(public email: string, public name: string, public phone: string) {}
}

export class Files {
  constructor(
    public graveProfilePicture: smallFile | null,
    public farewellLetter: smallFile | null,
    public videoSpeech: smallFile | null,
    public testament: smallFile | null
  ) {}
}

export class smallFile {
  constructor(public file: File, public name: string) {}
}

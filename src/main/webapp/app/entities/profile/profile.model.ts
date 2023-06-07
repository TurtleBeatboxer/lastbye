import { IUser } from 'app/entities/user/user.model';

export interface IProfile {
  id: number;
  surname?: string | null;
  phone?: number | null;
  prefix?: string | null;
  burialMethod?: string | null;
  clothes?: string | null;
  placeOfCeremony?: string | null;
  photo?: string | null;
  graveInscription?: string | null;
  spotify?: string | null;
  guests?: string | null;
  notInvited?: string | null;
  obituary?: string | null;
  purchasedPlace?: boolean | null;
  ifPurchasedOther?: string | null;
  flowers?: boolean | null;
  ifFlowers?: string | null;
  farewellLetter?: string | null;
  speech?: string | null;
  videoSpeech?: string | null;
  testament?: string | null;
  accessesForRelatives?: string | null;
  other?: string | null;
  codeQR?: string | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewProfile = Omit<IProfile, 'id'> & { id: null };

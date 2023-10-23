export let convertBurialTypeToText = number => {
  switch (number) {
    case 0:
      return 'coffin';
    case 1:
      return 'cremation';
  }
};

export let getCremationType = number => {
  switch (number) {
    case 0:
      return 'sea';
    case 1:
      return 'vinyl';
    case 2:
      return 'urn';
    case 3:
      return 'jewellery';
  }
};

export let getMusicType = number => {
  switch (number) {
    case 0:
      return 'live';
    case 1:
      return 'playlist';
    case 2:
      return 'noMusic';
    case 3:
      return 'other';
  }
  return '';
};

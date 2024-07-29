import { TestBed } from '@angular/core/testing';

import { UpdateEtatTacheService } from './update-etat-tache.service';

describe('UpdateEtatTacheService', () => {
  let service: UpdateEtatTacheService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateEtatTacheService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

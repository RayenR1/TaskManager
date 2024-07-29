import { TestBed } from '@angular/core/testing';

import { StatNbrEquipesService } from './stat-nbr-equipes.service';

describe('StatNbrEquipesService', () => {
  let service: StatNbrEquipesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatNbrEquipesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

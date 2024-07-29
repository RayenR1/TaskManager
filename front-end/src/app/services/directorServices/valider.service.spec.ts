import { TestBed } from '@angular/core/testing';

import { ValiderService } from './valider.service';

describe('ValiderService', () => {
  let service: ValiderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValiderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

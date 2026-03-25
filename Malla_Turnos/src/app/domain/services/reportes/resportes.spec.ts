import { TestBed } from '@angular/core/testing';

import { Resportes } from './resportes';

describe('Resportes', () => {
  let service: Resportes;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Resportes);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

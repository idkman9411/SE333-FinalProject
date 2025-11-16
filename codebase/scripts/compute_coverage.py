import csv
from pathlib import Path
p = Path('target/site/jacoco/jacoco.csv')
if not p.exists():
    print('NO_CSV')
    raise SystemExit(1)
inst_cov = 0
inst_miss = 0
with p.open() as f:
    reader = csv.DictReader(f)
    for r in reader:
        inst_cov += int(r['INSTRUCTION_COVERED'])
        inst_miss += int(r['INSTRUCTION_MISSED'])

if inst_cov + inst_miss == 0:
    print('NO_INSTRUCTIONS')
else:
    pct = inst_cov / (inst_cov + inst_miss) * 100
    print(f'{pct:.2f}')
    print(f'covered={inst_cov} missed={inst_miss} total={inst_cov+inst_miss}')

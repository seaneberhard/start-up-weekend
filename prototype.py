import datetime as dt

class ParacetamolCounter:
  def __init__(self):
    self.last = [dt.datetime.min] * 4
    
  def pop(self):
    self.last = [dt.datetime.now()] + self.last[0:]
    
  def canPop(self):
    return self.timeLeft() == dt.timedelta(0)
    
  def timeLeft(self):
    partyTime = max(self.last[0] + dt.timedelta(hours = 5), self.last[3] + dt.timedelta(days = 1))
    return max(dt.timedelta(0), partyTime - dt.datetime.now())
    

para = ParacetamolCounter()
while raw_input("Pop a pill?") != "stop":
  if para.canPop():
    para.pop()
    print "Get in!"
  else:
    print "Sorry, can't yet! You have to wait another {}.".format(para.timeLeft())
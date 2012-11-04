def McNuggets(n):
	if num%6==0 or num%9==0 or num%20==0:
		return True
	else:
		v6=num//6
		v9=num//9
		v20=num//20
		for i in range(v6+1):
			for j in range(v9+1):
				for k in range(v20+1):
					if 6*i+9*j+k*20==num:
						return True
		return False 
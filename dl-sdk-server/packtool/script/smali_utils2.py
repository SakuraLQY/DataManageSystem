# -*- coding: utf-8 -*-

import os
import os.path

import log_utils
import file_utils
import shutil


def renameSmaliPackage(smaliRoot, clsName, srcPackage, dstPackage):

	srcPkgPath = srcPackage.replace('.', '/')
	dstPkgPath = dstPackage.replace('.', '/')

	srcDir = os.path.join(smaliRoot, srcPkgPath)
	dstDir = os.path.join(smaliRoot, dstPkgPath)
	srcFile = os.path.join(srcDir, clsName+".smali")
	dstFile = os.path.join(dstDir, clsName+".smali")

	if not os.path.exists(srcFile):
		print("error renamePackage: file not exist - "+srcFile)
		return -1

	if not os.path.exists(dstDir):
		os.makedirs(dstDir)

	shutil.copy2(srcFile, dstFile)

	os.remove(srcFile)
	if len(os.listdir(srcDir)) == 0:
		os.rmdir(srcDir)

	file_utils.modifyFileContent(dstFile, srcPkgPath, dstPkgPath)


def get_smali_method_count(smaliFile, allMethods):

	if not os.path.exists(smaliFile):
		return 0

	f = open(smaliFile, encoding='UTF-8')
	lines = f.readlines()
	f.close()

	classLine = lines[0]
	classLine = classLine.strip()
	if not classLine.startswith(".class"):
		log_utils.error(smaliFile + " not startswith .class")
		return 0

	className = parse_class(classLine)
	#log_utils.debug("the class Name is "+className)



	count = 0
	for line in lines:
		line = line.strip()

		method = None
		tempClassName = className
		if line.startswith(".method"):
			method = parse_method_default(className, line)
		elif line.startswith("invoke-"):
			tempClassName, method = parse_method_invoke(line)

		if method is None:
			continue

		#log_utils.debug("the method is "+method)

		if tempClassName not in allMethods:
			allMethods[tempClassName] = list()


		if method not in allMethods[tempClassName]:
			count = count + 1
			allMethods[tempClassName].append(method)
		else:
			pass
			#log_utils.debug(method + " is already exists in allMethods.")

	return count



def parse_class(line):

	if not line.startswith(".class"):
		log_utils.error("line parse error. not startswith .class : "+line)
		return None

	blocks = line.split()
	return blocks[len(blocks)-1]



def parse_method_default(className, line):
	if not line.startswith(".method"):
		log_utils.error("the line parse error in parse_method_default:"+line)
		return None

	blocks = line.split()
	return blocks[len(blocks)-1]


def parse_method_invoke(line):
	if not line.startswith("invoke-"):
		log_utils.error("the line parse error in parse_method_invoke:"+line)

	blocks = line.split("->")
	method = blocks[len(blocks)-1]

	preblocks = blocks[0].split(",")
	className = preblocks[len(preblocks)-1]
	className = className.strip()

	return className,method



























